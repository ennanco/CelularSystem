#!/bin/sh

JAVA_VIRTUAL_MACHINE=$JAVA_HOME/bin/java

# Class path.
CP=../Build/Classes

# Start application.
$JAVA_VIRTUAL_MACHINE -classpath $CP \
    es.udc.tic.efernandez.celularsimulator.visualinterface.mainwindow.ADNPopulationWindow
