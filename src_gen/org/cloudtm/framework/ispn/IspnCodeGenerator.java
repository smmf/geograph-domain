package org.cloudtm.framework.ispn;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import dml.CodeGenerator;
import dml.CompilerArgs;
import dml.DomainClass;
import dml.DomainEntity;
import dml.DomainModel;
import dml.ExternalizationElement;
import dml.Role;
import dml.Slot;
import dml.ValueType;

public class IspnCodeGenerator extends CodeGenerator {

    protected static final String DOMAIN_CLASS_ROOT = "org.cloudtm.framework.ispn.AbstractDomainObject";

    public IspnCodeGenerator(CompilerArgs compArgs, DomainModel domainModel) {
	super(compArgs, domainModel);
    }

    // @Override
    // public void generateCode() {
    //     super.generateCode();
    // }

    @Override
    protected void generateFilePreamble(String subPackageName, PrintWriter out) {
        super.generateFilePreamble(subPackageName, out);
        println(out, "import org.cloudtm.framework.ispn.IspnTxManager;");
        println(out, "import org.cloudtm.framework.ispn.collections.bplustree.BPlusTree;");
        newline(out);
    }

    // @Override
    // protected void generateBaseClass(DomainClass domClass, PrintWriter out) {
    //     super.generateBaseClass(domClass, out);
    // }

    @Override
    protected void generateBaseClassBody(DomainClass domClass, PrintWriter out) {
        generateStaticSlots(domClass, out);
        newline(out);

        generateDefaultConstructor(domClass.getBaseName(), out);
        generateSlotsAccessors(domClass.getSlots(), out);
        generateRoleSlotsMethods(domClass.getRoleSlots(), out);
    }

    @Override
    protected void generateStaticRoleSlots(Role role, PrintWriter out) {
        onNewline(out);

        Role otherRole = role.getOtherRole();

        // The Role slot
        String roleType = getRoleType(role);
        printWords(out, "public", "static", roleType, getRoleHandlerName(role, false), "=", "new", roleType);
        print(out, "(");
        print(out, getRoleArgs(role));
        print(out, ")");
        newBlock(out);
        if (role.getMultiplicityUpper() == 1) {
            generateRoleMethodAdd(role, otherRole, out);
            generateRoleMethodRemove(role, otherRole, out);
        } else {
            generateRoleClassGetter(role, otherRole, out);
        }
        generateRoleMethodGetInverseRole(role, otherRole, out);
        closeBlock(out, false);
        println(out, ";");
    }

    protected void generateDefaultConstructor(String classname, PrintWriter out) {
        printMethod(out, "public", "", classname);
        startMethodBody(out);
        endMethodBody(out);
    }

    protected void generateRoleMethodAdd(Role role, Role otherRole, PrintWriter out) {
        boolean multOne = (role.getMultiplicityUpper() == 1);
        
        String otherRoleTypeFullName = getTypeFullName(otherRole.getType());
        String roleTypeFullName = getTypeFullName(role.getType());

        printMethod(out, "public", "void", "add",
                    makeArg(otherRoleTypeFullName, "o1"),
                    makeArg(roleTypeFullName, "o2"),
                    makeArg(makeGenericType("dml.runtime.Relation",otherRoleTypeFullName,roleTypeFullName), "relation"));
        startMethodBody(out);
        print(out, "if (o1 != null)");
        newBlock(out);
        println(out, roleTypeFullName + " old2 = o1.get" + capitalize(role.getName()) + "();");
        print(out, "if (o2 != old2)");
        newBlock(out);
        println(out, "relation.remove(o1, old2);");
        print(out, "o1.set" + capitalize(role.getName()) + "$unidirectional(o2);");
        closeBlock(out, false);
        closeBlock(out, false);
        endMethodBody(out);
    }

    protected void generateRoleMethodRemove(Role role, Role otherRole, PrintWriter out) {
        boolean multOne = (role.getMultiplicityUpper() == 1);
        
        String otherRoleTypeFullName = getTypeFullName(otherRole.getType());
        String roleTypeFullName = getTypeFullName(role.getType());

        printMethod(out, "public", "void", "remove",
                    makeArg(otherRoleTypeFullName, "o1"),
                    makeArg(roleTypeFullName, "o2"));
        startMethodBody(out);
        print(out, "if (o1 != null)");
        newBlock(out);
        print(out, "o1.set" + capitalize(role.getName()) + "$unidirectional(null);");
        closeBlock(out, false);
        endMethodBody(out);
    }

    // In this class this method is only invoked when the role's multiplicity is > 1
    @Override
    protected void generateRoleClassGetter(Role role, Role otherRole, PrintWriter out) {
        print(out, "public ");
        print(out, makeGenericType("dml.runtime.RelationBaseSet", getTypeFullName(role.getType())));
        print(out, " ");
        print(out, "getSet(");
        print(out, getTypeFullName(otherRole.getType()));
        print(out, " o1)");
        startMethodBody(out);
        print(out, "return (" + getConcreteSetTypeDeclarationFor(role) + ")");
        print(out, "o1.get");
        print(out, capitalize(role.getName()));
        print(out, "();");
        endMethodBody(out);
    }

    protected void generateRoleMethodGetInverseRole(Role role, Role otherRole, PrintWriter out) {
        // the getInverseRole method
        String inverseRoleType = makeGenericType("dml.runtime.Role",
                                                 getTypeFullName(role.getType()),
                                                 getTypeFullName(otherRole.getType()));
        printMethod(out, "public", inverseRoleType, "getInverseRole");
        startMethodBody(out);
        print(out, "return ");
        if (otherRole.getName() == null) {
            print(out, "new ");
            print(out, getRoleType(otherRole));
            print(out, "(this)");
        } else {
            print(out, getRoleHandlerName(otherRole, true));
        }
        print(out, ";");
        endMethodBody(out);
    }

    @Override
    protected void generateSlot(Slot slot, PrintWriter out) {
        onNewline(out);
        printWords(out, "private", slot.getTypeName(), slot.getName());
        print(out, ";");
    }

    @Override
    protected void generateGetterBody(String slotName, String typeName, PrintWriter out) {
        println(out, "Object obj = IspnTxManager.cacheGet(getOid() + \":" + slotName + "\");");

        String defaultValue;
        PrimitiveToWrapperEntry wrapperEntry = findWrapperEntry(typeName);
        if (wrapperEntry != null) { // then it is a primitive type
            defaultValue = wrapperEntry.defaultPrimitiveValue;
        } else {
            defaultValue = "null";
        }
        println(out, "if (obj == null || obj instanceof NullClass) return " + defaultValue + ";");
        print(out, "return (" + getReferenceType(typeName) + ")obj;");
    }

    @Override
    protected void generateSetterBody(String setterName, String slotName, String typeName, PrintWriter out) {
        String setterExpression;
	if (findWrapperEntry(typeName) != null) { // then it is a primitive type
            setterExpression = slotName;
        } else {
            setterExpression = "(" + slotName + " == null ? NULL_OBJECT : " + slotName + ")";
        }
        print(out, "IspnTxManager.cachePut(getOid() + \":" + slotName + "\", " + setterExpression + ");");
    }

    protected String getSetTypeDeclarationFor(Role role) {
        String elemType = getTypeFullName(role.getType());
        return makeGenericType("java.util.Set", elemType);
    }

    protected String getConcreteSetTypeDeclarationFor(Role role) {
        String elemType = getTypeFullName(role.getType());
        String thisType = getTypeFullName(role.getOtherRole().getType());
        return makeGenericType(getRelationAwareBaseTypeFor(role), thisType, elemType);
    }

    @Override
    protected void generateRoleSlotMethodsMultOne(Role role, PrintWriter out) {
        super.generateRoleSlotMethodsMultOne(role, out);

        String typeName = getTypeFullName(role.getType());
        String slotName = role.getName();
        String capitalizedSlotName = capitalize(slotName);
        String setterName = "set" + capitalizedSlotName;

        String methodModifiers = getMethodModifiers();

        // internal setter, which does not inform the relation
        newline(out);
        printMethod(out, methodModifiers, "void", setterName + "$unidirectional", makeArg(typeName, slotName));
        startMethodBody(out);
        print(out, "IspnTxManager.cachePut(getOid() + \":" + slotName + "\", (" + slotName + " == null ? NULL_OBJECT : " + slotName + ".getOid()));");
        endMethodBody(out);
    }

    @Override
    protected void generateRoleGetter(String slotName, String typeName, PrintWriter out) {
        newline(out);
        printFinalMethod(out, "public", typeName, "get" + capitalize(slotName));
        startMethodBody(out);
        println(out, "Object oid = IspnTxManager.cacheGet(getOid() + \":" + slotName + "\");");
        print(out, "return (oid == null || oid instanceof NullClass ? null : (" + typeName + ")fromOid((String)oid));");
        endMethodBody(out);
    }

    @Override
    protected void generateRoleSlotMethodsMultStar(Role role, PrintWriter out) {

        String typeName = getTypeFullName(role.getType());
        String slotName = role.getName();
        String capitalizedSlotName = capitalize(slotName);
        String methodModifiers = getMethodModifiers();

        generateRoleSlotMethodsMultStarGetter(role, out);
        generateRoleSlotMethodsMultStarSetter(role, out, methodModifiers, capitalizedSlotName, typeName, slotName);
        generateRoleSlotMethodsMultStarRemover(role, out, methodModifiers, capitalizedSlotName, typeName, slotName);
        generateRoleSlotMethodsMultStarSet(role, out, methodModifiers, capitalizedSlotName, typeName);
        generateRoleSlotMethodsMultStarCount(role, out, methodModifiers, capitalizedSlotName);
        generateRoleSlotMethodsMultStarHasAnyChild(role, out, methodModifiers, capitalizedSlotName);
        generateRoleSlotMethodsMultStarHasChild(role, out, methodModifiers, capitalizedSlotName, typeName, slotName);
        generateIteratorMethod(role, out);
    }

    protected void generateRoleSlotMethodsMultStarGetter(Role role, PrintWriter out) {
        newline(out);
        printFinalMethod(out, "public", getSetTypeDeclarationFor(role), "get" + capitalize(role.getName()));
        startMethodBody(out);

        println(out, makeGenericType("BPlusTree", getTypeFullName(role.getType())) + " internalSet;");
        println(out, "Object oid = IspnTxManager.cacheGet(getOid() + \":" + role.getName() + "\");");
        print(out, "if (oid == null || oid instanceof NullClass)");
        newBlock(out);
        println(out, "internalSet = new " + makeGenericType("BPlusTree", getTypeFullName(role.getType())) + "();");
        println(out, "internalSet.initRoot();");
        println(out, "IspnTxManager.staticSave(internalSet);");
        print(out, "IspnTxManager.cachePut(getOid() + \":" + role.getName() + "\", internalSet.getOid());");
        closeBlock(out, false);
        print(out, " else");
        newBlock(out);
        println(out, "internalSet = (" + makeGenericType("BPlusTree", getTypeFullName(role.getType())) + ")fromOid((String)oid);");
        print(out, "// no need to test for null.  The entry must exist for sure.");
        closeBlock(out);
        print(out, "return new " + getRelationAwareBaseTypeFor(role) + "(this, " + getRelationSlotNameFor(role) + ", internalSet);");
        endMethodBody(out);
    }

    protected void generateRoleSlotMethodsMultStarSetter(Role role, PrintWriter out, String methodModifiers,
                                                         String capitalizedSlotName, String typeName, String slotName) {
        newline(out);
        String adderMethodName = getAdderMethodName(role);
        printFinalMethod(out, methodModifiers, "void", adderMethodName,
                         makeArg(typeName, slotName));
        startMethodBody(out);
        generateRelationAddMethodCall(role, slotName, null, out);
        endMethodBody(out);
    }

    protected void generateRoleSlotMethodsMultStarRemover(Role role, PrintWriter out, String methodModifiers,
                                                          String capitalizedSlotName, String typeName, String slotName) {
        String removerMethodName = getRemoverMethodName(role);

        newline(out);
        printMethod(out, methodModifiers, "void", removerMethodName, makeArg(typeName, slotName));
        startMethodBody(out);
        generateRelationRemoveMethodCall(role, slotName, out);
        endMethodBody(out);
    }

    protected void generateRoleSlotMethodsMultStarCount(Role role, PrintWriter out,
	    String methodModifiers, String capitalizedSlotName) {
        newline(out);
        printMethod(out, methodModifiers, "int", "get" + capitalizedSlotName + "Count");
        startMethodBody(out);
        printWords(out, "return get" + capitalizedSlotName + "().size();");
        endMethodBody(out);
    }

    protected void generateRoleSlotMethodsMultStarHasAnyChild(Role role, PrintWriter out,
	    String methodModifiers, String capitalizedSlotName) {
        newline(out);
        printMethod(out, methodModifiers, "boolean", "hasAny" + capitalizedSlotName);
        startMethodBody(out);
        printWords(out, "return (get" + capitalizedSlotName + "().size() != 0);");
        endMethodBody(out);
    }

    protected void generateRoleSlotMethodsMultStarHasChild(Role role, PrintWriter out, String methodModifiers,
	    String capitalizedSlotName, String typeName, String slotName) {
        newline(out);
        printMethod(out, methodModifiers, "boolean", "has" + capitalizedSlotName, makeArg(typeName, slotName));
        startMethodBody(out);
        printWords(out, "return get" + capitalizedSlotName + "().contains(" + slotName + ");");
        endMethodBody(out);
    }

    protected void generateRoleSlotMethodsMultStarSet(Role role, PrintWriter out, String methodModifiers,
	    String capitalizedSlotName, String typeName) {
        newline(out);
        printMethod(out, methodModifiers, makeGenericType("java.util.Set", typeName), "get" + capitalizedSlotName + "Set");
        startMethodBody(out);
        print(out, "return get" + capitalizedSlotName + "();");
        endMethodBody(out);
    }

    protected void generateIteratorMethod(Role role, PrintWriter out) {
        newline(out);
        printFinalMethod(out, "public", makeGenericType("java.util.Iterator", getTypeFullName(role.getType())), "get"
        	+ capitalize(role.getName()) + "Iterator");
        startMethodBody(out);
        printWords(out, "return get" + capitalize(role.getName()) + "().iterator();");
        endMethodBody(out);
    }

    @Override
    protected String getNewRoleStarSlotExpression(Role role) {
        return getNewRoleStarSlotExpressionWithBackingSet(role, role.getName());
    }

    protected String getNewRoleStarSlotExpressionWithEmptySet(Role role) {
        StringBuilder buf = new StringBuilder();
        buf.append("new ");
        buf.append(makeGenericType("java.util.HashSet", getTypeFullName(role.getType())));
        buf.append("()");
        return getNewRoleStarSlotExpressionWithBackingSet(role, buf.toString());
    }

    protected String getNewRoleStarSlotExpressionWithBackingSet(Role role, String theSet) {
        StringBuilder buf = new StringBuilder();

        // generate the relation aware collection
        String thisType = getTypeFullName(role.getOtherRole().getType());
        buf.append("new ");
        buf.append(getRelationAwareTypeFor(role));
        buf.append("(");
        buf.append(theSet);
        buf.append(", ");
        buf.append("(");
        buf.append(thisType);
        buf.append(")this, ");
        buf.append(getRelationSlotNameFor(role));
        buf.append(")");

        return buf.toString();
    }

    @Override
    protected String getRoleOneBaseType() {
        return "dml.runtime.Role";
    }

    @Override
    protected String getDomainClassRoot() {
        return DOMAIN_CLASS_ROOT;
    }

    @Override
    protected String getRelationAwareBaseTypeFor(Role role) {
        // FIXME: handle other types of collections other than sets
        return "org.cloudtm.framework.ispn.RelationSet";
    }
}
