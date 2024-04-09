package the_ride.the_ride_backend.tools;

import org.hibernate.type.Type;
import org.hibernate.Interceptor;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.Transaction;

public class EntityInterceptor implements Interceptor {
    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) {
        System.out.println("Entity being saved: " + entity);
        return false;
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState,
                             Object[] previousState, String[] propertyNames, Type[] types) {
        return (id == null || id instanceof Serializable)
                && this.onFlushDirty(entity, (Serializable) id, currentState, previousState, propertyNames, types);
    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {
        if (!tx.isActive()) {
            System.out.println("Transaction completed (commit or rollback).");
        }
    }

    @Override
    public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) {
        System.out.println("Entity being deleted: " + entity);
    }

    @Override
    public void preFlush(Iterator entities) {
    }

    @Override
    public void postFlush(Iterator entities) {
    }
}
