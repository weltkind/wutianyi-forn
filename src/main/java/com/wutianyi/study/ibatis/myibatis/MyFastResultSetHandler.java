package com.wutianyi.study.ibatis.myibatis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

public class MyFastResultSetHandler
{
    protected final Configuration configuration;
    protected final TypeHandlerRegistry typeHandlerRegistry;
    protected final ObjectFactory objectFactory;
    protected final MappedStatement mappedStatement;

    public MyFastResultSetHandler(MappedStatement _mappedStatement)
    {
        this.mappedStatement = _mappedStatement;
        this.configuration = mappedStatement.getConfiguration();
        typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        objectFactory = configuration.getObjectFactory();
    }

    public List handleResultSets(SimpleRowSet results)
    {
        final List multipleResults = new ArrayList();
        final List<ResultMap> resultsMaps = mappedStatement.getResultMaps();
        int resultMapCount = resultsMaps.size();
        int resultSetCount = 0;

        validateResultMapsCount(results, resultMapCount);
        while (results != null && resultMapCount > resultSetCount)
        {
            final ResultMap resultMap = resultsMaps.get(resultSetCount);
            handleResultSet(results, resultMap, multipleResults);
            resultSetCount++;
        }
        return collapseSingleResultList(multipleResults);
    }

    private List collapseSingleResultList(List multipleResults)
    {
        return multipleResults;
    }

    private void handleResultSet(SimpleRowSet results, ResultMap resultMap, List multipleResults)
    {
        handleRowValues(results, resultMap, multipleResults);
    }

    private void handleRowValues(SimpleRowSet results, ResultMap resultMap, List multipleResults)
    {
        while (results.next())
        {
            Object rowValue = getRowValue(results, resultMap);
            multipleResults.add(rowValue);
        }
    }

    private Object getRowValue(SimpleRowSet results, ResultMap resultMap)
    {
        final List<String> mappedColumnNames = new ArrayList<String>();
        final List<String> unmappedColumnNames = new ArrayList<String>();

        Object resultObject = createResultObject(results, resultMap);
        if (resultObject != null && !typeHandlerRegistry.hasTypeHandler(resultMap.getType()))
        {
            final MetaObject metaObject = configuration.newMetaObject(resultObject);
            loadMappedAndUnmappedColumnNames(results, resultMap, mappedColumnNames, unmappedColumnNames);
            applyAutomaticMapping(results, metaObject);
            applyPropertyMapping(results, resultMap, metaObject);
        }
        return resultObject;
    }

    private void applyAutomaticMapping(SimpleRowSet results, MetaObject metaObject)
    {
        String[] properties = metaObject.getGetterNames();
        for (String property : properties)
        {
            Object value = getPropertyMappingValue(results, property, metaObject, metaObject.getSetterType(property));
            if (null != value)
            {
                metaObject.setValue(property, value);
            }
        }
    }

    private void applyPropertyMapping(SimpleRowSet results, ResultMap resultMap, MetaObject metaObject)
    {
        final List<ResultMapping> propertyMappings = resultMap.getPropertyResultMappings();
        for (ResultMapping propertyMapping : propertyMappings)
        {
            final String column = propertyMapping.getColumn();

            Object value = getPropertyMappingValue(results, column, metaObject, propertyMapping.getJavaType());
            if (null != value)
            {
                final String property = propertyMapping.getProperty();
                metaObject.setValue(property, value);
            }
        }
    }

    private Object getPropertyMappingValue(SimpleRowSet results, String column, MetaObject metaObject,
            Class type)
    {
        Object value = null;
        try
        {
            if (type.equals(Date.class))
            {
                value = new Date();
            }
            else
            {

                String result = results.getString(column);
                value = MyTypeHandler.stringToType(result, type);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return value;
    }

    private void loadMappedAndUnmappedColumnNames(SimpleRowSet results, ResultMap resultMap,
            List<String> mappedColumnNames, List<String> unmappedColumnNames)
    {
        mappedColumnNames.clear();
        unmappedColumnNames.clear();

    }

    private Object createResultObject(SimpleRowSet results, ResultMap resultMap)
    {
        final Class resultType = resultMap.getType();

        return objectFactory.create(resultType);
    }

    private void validateResultMapsCount(Object results, int resultMapCount)
    {

    }

}
