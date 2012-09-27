require 'java'


CURRENT_PATH = File.expand_path File.dirname(__FILE__)
DML_CONF_PATH = File.join(CURRENT_PATH, 'src', 'common', 'dml')
ISPN_CONF_PATH = File.join(CURRENT_PATH, 'config')
$CLASSPATH << ISPN_CONF_PATH

# load the jars
LIB_PATH = File.join(CURRENT_PATH, 'lib')
$CLASSPATH << LIB_PATH
Dir[File.join(LIB_PATH, '*.jar')].each{|jar|
  #puts "Loading JAR: #{jar}"
  require jar
}

# load the domain model jar
DIST_PATH = File.join(CURRENT_PATH, 'dist')
$CLASSPATH << DIST_PATH
require File.join(DIST_PATH, 'geograph-domain.jar')


# Load Fenix Framework
FenixConfig = Java::PtIstFenixframework::Config
FenixFramework = Java::PtIstFenixframework::FenixFramework

# Load the domain models
Agent = Java::ItAlgoGeographDomain::Agent
Post = Java::ItAlgoGeographDomain::Post
FenixRoot = Java::ItAlgoGeographDomain::Root

# Load the CloudTM glue framework
CloudTmInit = Java::OrgCloudtmFramework::Init
CloudTmTxSystem = Java::OrgCloudtmFramework::TxSystem
CloudTmConfig = Java::OrgCloudtmFramework::CloudtmConfig


#FenixTransactionManager = Java::OrgCloudtmFrameworkFenix::FFTxManager
IspnTransactionManager = Java::OrgCloudtmFrameworkIspn::IspnTxManager
IllegalWriteException = Java::PtIstFenixframeworkPstm::IllegalWriteException
CommitException = Java::Jvstm::CommitException
WriteOnReadException = Java::Jvstm::WriteOnReadException
UnableToDetermineIdException = Java::PtIstFenixframeworkPstm::AbstractDomainObject::UnableToDetermineIdException


class TmpIspnTransactionManager
  def withTransaction(&block)
    result = nil
    try_read_only = true

    while(true) do
      Java::PtIstFenixframeworkPstm::Transaction.begin(try_read_only)
      finished = false
      begin
        result = block.call
        Java::PtIstFenixframeworkPstm::Transaction.commit
        finished = true
        return result
      rescue CommitException => ce
        FenixTransaction.abort
        finished = true
      rescue WriteOnReadException => wore
        puts "jvstm.WriteOnReadException"
        Java::PtIstFenixframeworkPstm::Transaction.abort
        finished = true
        try_read_only = false
      rescue UnableToDetermineIdException => unableToDetermineIdException
        puts "Restaring TX: unable to determine id. Cause: #{unableToDetermineIdException.getCause}"
        puts unableToDetermineIdException.to_s
        Java::PtIstFenixframeworkPstm::Transaction.abort
        finished = true
      ensure
        unless finished
          Java::PtIstFenixframeworkPstm::Transaction.abort
        end
      end
    end
  end

end

# In order to bypass the use of the constructor with closure, that causes problems
# in the jruby binding.
# Here we open the Fenix Config class and we define a method that permits to
# valorize the same protected variables managed by the standard constructor.
class FenixConfig
  # Accepts an hash of params, keys are instance variables of FenixConfig class
  # and values are used to valorize these variables.
  def init params
    params.each do |name, value|
      set_param(name, value)
    end
  end

  private

  # Sets an instance variable value.
  def set_param(name, value)
    # Jruby doesn't offer accessors for the protected variables.
    field = self.java_class.declared_field name
    field.accessible = true
    field.set_value Java.ruby_to_java(self), Java.ruby_to_java(value)
  end
end

class CloudTmTransactionManager
  #cattr_accessor :manager

  class << self
    def manager
      @manager
    end

    def manager=(man)
      @manager = man
    end
  end
end

# This is the Fenix Framework loader. It provides a simple way to
# run the framework initialization process.
class FenixLoader
  # Load the Fenix Framework.
  # Options:
  # => dml: the dml file name
  # => conf: the configuration file name
  # => root: the root class
  def self.load(options)
    config = FenixConfig.new
    config.init(
      :domainModelPath => File.join(DML_CONF_PATH, options[:dml]),
      :dbAlias => File.join(ISPN_CONF_PATH, options[:conf]),
      :rootClass => FenixRoot.java_class,
      :repositoryType => FenixConfig::RepositoryType::INFINISPAN
    )

    CloudTmInit.initializeTxSystem(config, CloudTmConfig::Framework::ISPN)
    CloudTmTransactionManager.manager = CloudTmTxSystem.getManager
  end
end


class Agent
  # fake to_json method
  def to_json
    {
      :id => oid,
      :latitude => latitude.to_s,
      :longitude => longitude.to_s
    }
  end

  class << self

    def create attrs = {}
      manager = CloudTmTransactionManager.manager
      manager.withTransaction do

        instance = Agent.new
        attrs.each do |attr, value|
          instance.send("#{attr}=", value)
        end
        manager.getRoot.add_agents instance
		manager.save instance
		
        instance.to_json
      end
    end

    def all
      manager = CloudTmTransactionManager.manager
      manager.withTransaction do
        _agents = manager.getRoot.getAgents
        _agents.map(&:to_json)
      end
    end

  end
end


FenixLoader.load({
    :dml => 'geograph.dml',
    :conf => 'infinispanFile.xml'
  })


go = Agent.create({
    :latitude => java.math.BigDecimal.new("45.4324"),
    :longitude => java.math.BigDecimal.new("23.6543")
  })
puts "Created #{go.inspect}"

Agent.all.each do |geo_object|
  puts "Created agent object: lat = #{geo_object[:latitude]} - lon = #{geo_object[:longitude]}"
end

#puts "Json version: #{FenixGeoObject.all.to_json}"

Agent.create({
    :latitude => java.math.BigDecimal.new("72.6426"),
    :longitude => java.math.BigDecimal.new("32.5425")
  })

puts "Agents are"
Agent.all.each do |geo_object|
  puts "geo object: lat = #{geo_object[:latitude]} - lon = #{geo_object[:longitude]}"
end


	  

#_manager = CloudTmTransactionManager.manager
#_manager.withTransaction do
#   _gobjects = _manager.getRoot.getAgents
#   go1 = _gobjects.toArray[0]
#   go2 = _gobjects.toArray[1]
#   go1.addIncoming(go2)
#   go1.latitude = nil
#end

#_manager.withTransaction do
#  _manager.getRoot.getGeoObjects.each do |gobj|
#    if gobj.hasAnyIncoming
#      puts "Link from: #{gobj.incoming.toArray[0].to_json.inspect}"
#      puts "Link to: #{gobj.incoming.toArray[0].outcoming.toArray[0].to_json.inspect}"
#      gobj.removeIncoming(gobj.incoming.toArray[0])
#    end
#  end
#end
