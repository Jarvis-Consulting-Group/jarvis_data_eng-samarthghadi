package ca.jrvs.apps.trading.Model.Domain;

public interface Entity<ID> {
    ID getId();

    void setId(ID id);
}
