package edu.cornell.ics.entities;

import edu.cornell.ics.interfaces.IQuery;

import java.util.Hashtable;

public class Query implements IQuery {
    private String queryString;
    private Hashtable<String, Object> parameters;

    public Query(String queryString) {
        this.queryString = queryString;
        parameters = new Hashtable<String, Object>();
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public Hashtable<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public void setParameters(Hashtable<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public void setParameter(String paramText, Object paramObject) {
        this.parameters.put(paramText, paramObject);
    }
}
