package com.wutianyi.study.neo.acl;

import org.neo4j.graphdb.RelationshipType;

public enum RelTypes implements RelationshipType
{
    PRINCIPALS, PRINCIPAL, IS_MEMBER_OF_GROUP, SECURITY, CONTENT_ROOTS, CONTENT_ROOT, HAS_CHILD_CONTENT;
}
