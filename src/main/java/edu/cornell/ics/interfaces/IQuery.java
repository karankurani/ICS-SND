package edu.cornell.ics.interfaces;

import java.util.Hashtable;

public interface IQuery {
    String getQueryString();

    void setQueryString(String queryString);

    Hashtable<String, Object> getParameters();

    void setParameters(Hashtable<String, Object> parameters);

    void setParameter(String paramText, Object paramObject);
}
