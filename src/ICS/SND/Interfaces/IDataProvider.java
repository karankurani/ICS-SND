package ICS.SND.Interfaces;

import java.util.List;

public interface IDataProvider<T> {

    void Save(T item);

    void Update(T item);

    T Load(IQuery q);

    List<T> List(IQuery q);

    void Delete(T item);

    List<T> ListBySQL(String query);
}