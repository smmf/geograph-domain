package pt.ist.fenixframework;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;

public class ValueTypeSerializationGenerator {
    
    // VT: Decimal
    public static class Serialized$Decimal implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        private java.lang.String toString;
        private  Serialized$Decimal(java.math.BigDecimal obj) {
            this.toString = (java.lang.String)obj.toString();
            
        }
        
    }
    public static Serialized$Decimal serialize$Decimal(java.math.BigDecimal obj) {
        return (obj == null) ? null : new Serialized$Decimal(obj);
    }
    public static java.math.BigDecimal deSerialize$Decimal(Serialized$Decimal obj) {
        return (obj == null) ? null : (java.math.BigDecimal)new java.math.BigDecimal(obj.toString);
    }
    
    // VT: TreeMap
    public static class Serialized$TreeMap implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        private byte[] pt_ist_fenixframework_pstm_collections_bplustree_AbstractNode_externalizeTreeMap;
        private  Serialized$TreeMap(java.util.TreeMap obj) {
            this.pt_ist_fenixframework_pstm_collections_bplustree_AbstractNode_externalizeTreeMap = (byte[])pt.ist.fenixframework.pstm.collections.bplustree.AbstractNode.externalizeTreeMap(obj);
            
        }
        
    }
    public static Serialized$TreeMap serialize$TreeMap(java.util.TreeMap obj) {
        return (obj == null) ? null : new Serialized$TreeMap(obj);
    }
    public static java.util.TreeMap deSerialize$TreeMap(Serialized$TreeMap obj) {
        return (obj == null) ? null : (java.util.TreeMap)pt.ist.fenixframework.pstm.collections.bplustree.AbstractNode.internalizeTreeMap(obj.pt_ist_fenixframework_pstm_collections_bplustree_AbstractNode_externalizeTreeMap);
    }
    
    // VT: TxNumber
    public static class Serialized$TxNumber implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        private long pt_ist_fenixframework_TxNumber_externalize;
        private  Serialized$TxNumber(pt.ist.fenixframework.TxNumber obj) {
            this.pt_ist_fenixframework_TxNumber_externalize = (long)pt.ist.fenixframework.TxNumber.externalize(obj);
            
        }
        
    }
    public static Serialized$TxNumber serialize$TxNumber(pt.ist.fenixframework.TxNumber obj) {
        return (obj == null) ? null : new Serialized$TxNumber(obj);
    }
    public static pt.ist.fenixframework.TxNumber deSerialize$TxNumber(Serialized$TxNumber obj) {
        return (obj == null) ? null : (pt.ist.fenixframework.TxNumber)pt.ist.fenixframework.TxNumber.internalize(obj.pt_ist_fenixframework_TxNumber_externalize);
    }
    
    // VT: CounterStats
    public static class Serialized$CounterStats implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        private int getMinValue;
        private int getMaxValue;
        private long getValueSum;
        private  Serialized$CounterStats(pt.ist.fenixframework.pstm.TransactionStatistics.CounterStats obj) {
            this.getMinValue = (int)obj.getMinValue();
            this.getMaxValue = (int)obj.getMaxValue();
            this.getValueSum = (long)obj.getValueSum();
            
        }
        
    }
    public static Serialized$CounterStats serialize$CounterStats(pt.ist.fenixframework.pstm.TransactionStatistics.CounterStats obj) {
        return (obj == null) ? null : new Serialized$CounterStats(obj);
    }
    public static pt.ist.fenixframework.pstm.TransactionStatistics.CounterStats deSerialize$CounterStats(Serialized$CounterStats obj) {
        return (obj == null) ? null : (pt.ist.fenixframework.pstm.TransactionStatistics.CounterStats)new pt.ist.fenixframework.pstm.TransactionStatistics.CounterStats(obj.getMinValue, obj.getMaxValue, obj.getValueSum);
    }
    
}
