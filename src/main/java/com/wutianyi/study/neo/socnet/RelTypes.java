package com.wutianyi.study.neo.socnet;

import org.neo4j.graphdb.RelationshipType;

public enum RelTypes implements RelationshipType
{
    PEF_PERSONS, A_PERSON, STATUS, NEXT, FRIEND;
}
