package ca.jrvs.apps.trading.DAO;

import ca.jrvs.apps.trading.Model.Domain.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import javax.sql.DataSource;

public class QuoteDao implements CrudRepository<Quote, String> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }
    @Override
    public Quote save(Quote quote) {
        if( existsById(quote.getTicker())) {
            int updatedRowNo = updateOne(quote);
            if (updatedRowNo != 1) {
                throw new DataRetrievalFailureException("Unable to update row");
            }
        } else {
            addOne(quote);
        }
        return quote;
    }

    private void addOne(Quote quote) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(quote);
        int check = simpleJdbcInsert.execute(source);
        if(check != 1) {
            throw new IncorrectResultSizeDataAccessException("Unable to insert",1,check);
        }
    }

    private int updateOne(Quote quote) {
        String update_sql = "UPDATE " + TABLE_NAME + " SET "
                + "last_price=?, bid_price=?, bid_size=?, ask_price=?, ask_size=? WHERE ticker=? ";
        return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
    }

    private Object[] makeUpdateValues (Quote quote) {
        Object[] objects = {
                quote.getLastPrice(),
                quote.getBidPrice(),
                quote.getBidSize(),
                quote.getAskPrice(),
                quote.getAskSize(),
                quote.getTicker()
        };
        return objects;
    }

    @Override
    public <S extends Quote> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Quote> findById(String s) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?";//"'" + id + "'";
        Quote quote = null;
        try {
            quote = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Quote.class), s);
        } catch(Exception e) {
            logger.error("Exception: record not found, so insert it as a new record", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return findById(s).isPresent();
    }

    @Override
    public Iterable<Quote> findAll() {
        return null;
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> strings) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Invalid ticker id");
        }

        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
        int check = jdbcTemplate.update(sql, s);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM " + TABLE_NAME;
        jdbcTemplate.update(sql);
    }

    @Override
    public void delete(Quote entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

//    @Override
//    public void deleteAllById(Iterable<? extends String> strings) {
//
//    }

    @Override
    public void deleteAll(Iterable<? extends Quote> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }

}