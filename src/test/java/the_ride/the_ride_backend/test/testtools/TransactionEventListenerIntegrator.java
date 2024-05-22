package the_ride.the_ride_backend.test.testtools;

//public class TransactionEventListenerIntegrator implements Integrator {
//
//    /**
//     * a custom insert listener is registered in eventListenerRegistry.appendListeners
//     * Other Event Listeners include  POST_COMMIT_UPDATE and POST_COMMIT_DELETE.
//     *  Spring’s TransactionSynchronizationManager allows for detailed transaction synchronization callbacks
//     *  that are aware of the transaction context, including successful commit and rollback scenarios.
//     */
//
//    @Override
//    public void integrate(@NotNull Metadata metadata, @NotNull SessionFactoryImplementor sessionFactory,
//                          SessionFactoryServiceRegistry serviceRegistry) {
//        final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
//        assert eventListenerRegistry != null;
//        eventListenerRegistry.appendListeners(EventType.POST_COMMIT_INSERT, new PostCommitInsertEventListener() {
//            @Override
//            public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
//                return false;
//            }
//
//            @Override
//            public void onPostInsertCommitFailed(PostInsertEvent postInsertEvent) {
//            }
//
//            @Override
//            public void onPostInsert(PostInsertEvent event) {
//                System.out.println("Entity inserted: " + event.getEntity());
//            }
//        });
//    }
//
//    @Override
//    public void disintegrate(@NotNull SessionFactoryImplementor sessionFactory,
//                             @NotNull SessionFactoryServiceRegistry serviceRegistry) {
//        // This method can be used to clean up any resources tied to the SessionFactory
//    }
//}
