# eToroBot

Lightweight day trading bot based upon the VWAP algorithm implementation and the eToro web interface

## Requirements
* Bot must be able to make (almost) instantaneous trades through the eToro web interface
* It must implement the *Volume Weighter Average Price (VWAP)* algorithm for making decisions

## Installation
* Set up [Maven](https://maven.apache.org/download.cgi) and [JDK 8](https://adoptopenjdk.net/) on your machine
* Run `mvn clean install`
* Run `mvn package` to deploy a JAR file
