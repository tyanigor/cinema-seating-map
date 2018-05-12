package net.rest.cinemaseatingmap.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Абстрактный DAO класс
 *
 * @param <PK>  тип первичного ключа
 * @param <T>   модель данных
 */
public abstract class AbstractDAO<PK extends Serializable, T> {

    /**
     * Фабрика сессий Hibernate
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Класс типа Class дженерика
     */
    private final Class<T> persistentClass;

    /**
     * Конструктор инициализирующий persistentClass
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    /**
     * Получение сессии от фабрики
     *
     * @return - сессия
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Получение модели по ключу
     *
     * @param key - первичный ключ
     * @return - модель данных
     */
    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }

    /**
     * Сохранение модели данных
     *
     * @param entity - модель данных
     */
    public void persist(T entity) {
        getSession().persist(entity);
    }

    /**
     * Удаление модели данных
     *
     * @param entity - модель данных
     */
    public void delete(T entity) {
        getSession().delete(entity);
    }

    /**
     * Получение всего списка моделей данных
     *
     * @return - список моделей данных
     */
    public List<T> list() {
        return (List<T>) createEntityCriteria().list();
    }

    /**
     * Создание критерия
     *
     * @return - критерий
     */
    protected Criteria createEntityCriteria() {
        return getSession().createCriteria(persistentClass);
    }
}
