require 'java'

CURRENT_PATH = File.expand_path File.dirname(__FILE__)
ISPN_CONF_PATH = File.join(CURRENT_PATH, 'config')
$CLASSPATH << ISPN_CONF_PATH

# load the jars
LIB_PATH = File.join(CURRENT_PATH, 'lib')
Dir[File.join(LIB_PATH, '*.jar')].each{|jar|
  next unless jar.match(/infinispan|log|jgroups|jboss/)
  #puts "Loading JAR: #{jar}"
  require jar
}

$CLASSPATH << LIB_PATH

DefaultCacheManager = Java::OrgInfinispanManager::DefaultCacheManager

manager = DefaultCacheManager.new(File.join(ISPN_CONF_PATH, "infinispanNoFile.xml"))
cache = manager.getCache()
cache.put("name", "ispn")
name = cache.get("name")
puts "name is #{name}"

