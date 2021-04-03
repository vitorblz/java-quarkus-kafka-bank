package br.com.vitor.bank.model;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class BalancesImpl implements Balances, PanacheRepository<Balance> {

    @Override
    public Optional<Balance> findByAccountId(String accountId) {
        return find("accountId", accountId).singleResultOptional();
    }

    @Override
    @Transactional
    public Balance recalculate(Transaction transaction) {
        var currentBalance = findByAccountId(transaction.getAccountId())
                .orElse(Balance.defaultInstance(transaction.getAccountId())).recalculateBalance(transaction);
        if (currentBalance.id == null) {
            persist(currentBalance);
        } else {
            update("value = ?1 where id = ?2", currentBalance.value, currentBalance.id);
        }
        return currentBalance;
    }

}
