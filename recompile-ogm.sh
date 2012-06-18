ant -f build_ogm.xml clean-all && 
ant clean &&
ant -f build_ogm.xml compile &&
ant compile
ant gen-jar
