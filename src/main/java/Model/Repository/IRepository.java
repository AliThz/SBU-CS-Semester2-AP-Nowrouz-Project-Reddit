package Model.Repository;

import java.util.ArrayList;

public interface IRepository<T_Entity, K_Id> {

    void insert(T_Entity entity);
    void insert(ArrayList<T_Entity> entities);
    void update(T_Entity entity);
    void delete(T_Entity entity);
    ArrayList<T_Entity> select();
    T_Entity select(K_Id id);

}
