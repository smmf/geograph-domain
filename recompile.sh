ant -f build_ispn.xml clean-all && 
ant clean &&
ant -f build_ispn.xml compile &&
ant gen-jar
