package com.anythingintellect.mydeliveries.db;

import com.anythingintellect.mydeliveries.model.Delivery;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ishan.dhingra on 03/09/17.
 */

public class RealmLocalStore implements LocalStore {

    private final Realm realm;

    public RealmLocalStore(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void saveDeliveries(final List<Delivery> deliveries) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(deliveries);
            }
        });
    }


    @Override
    public RealmResults<Delivery> getDeliveries() {
        return realm.where(Delivery.class).findAllSortedAsync("id", Sort.ASCENDING);
    }

    @Override
    public void dispose() {
        realm.close();
    }
}
