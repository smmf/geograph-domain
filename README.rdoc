== Geograph Domain Model (Ogm - Fenix - Infinispan)

This project is part of the Specific Targeted Research Project (STReP) Cloud-TM[http://www.cloudtm.eu] and is partially funded by the
European Commission under the Seventh Framework Programme for Research and Technological Development (FP7 - 2007-2013) with contract no. 257784.

This is the Cloud-TM domain model of Geograph[http://github.com/algorithmica/geograph].
 
== Getting started

In order to generate the Geograph domain model do the following:
1. Clone the project from the git repository:
    git clone git://github.com/algorithmica/geograph-domain.git
2. Generate the domain model using the following comand (you can find the dml in src/common/dml/geograph.dml):
    mvn clean package
3. Put the new domain model inside the Geograph folder lib/cloud_tm/jars and the dml inside lib/ispn/conf

***Note***  You need to copy the follwoeing files:

find ~/.m2/repository/pt/ist -name \*.jar | grep 2.0-cloudtm

in your lib folder (instead of fenix-framework jar as for DML v1)