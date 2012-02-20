package com.wutianyi.study.discoverygroup.neo;

import org.neo4j.graphdb.RelationshipType;

public enum RelTypes implements RelationshipType
{
    AUTHOR_ROOT, WORD_ROOT, AUTHOR, WORD, USE;

}
