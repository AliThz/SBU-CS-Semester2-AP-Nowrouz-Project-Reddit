import java.util.ArrayList;

public interface Repository<T_Entity> {

    void insert(T_Entity entity);
    void insert(ArrayList<T_Entity> entities);
    void update(T_Entity entity);
    void delete(T_Entity entity);
    ArrayList<T_Entity> select();

}
