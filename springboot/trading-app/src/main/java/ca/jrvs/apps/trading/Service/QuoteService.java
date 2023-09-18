package ca.jrvs.apps.trading.Service;

import ca.jrvs.apps.trading.DAO.MarketDataDao;
import ca.jrvs.apps.trading.DAO.QuoteDao;
import ca.jrvs.apps.trading.Model.Domain.IexQuote;

import ca.jrvs.apps.trading.Model.Domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    public IexQuote findIexQuoteByTicker(String ticker) {
        return marketDataDao.findById(ticker)
                .orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid"));
    }

    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();
        quote.setId(iexQuote.getSymbol());
        quote.setTicker(iexQuote.getSymbol());
        quote.setLastPrice(iexQuote.getLatestPrice());
        quote.setAskPrice(iexQuote.getIexAskPrice());
        quote.setBidPrice(iexQuote.getIexBidPrice());
        quote.setBidSize(iexQuote.getIexBidSize());
        quote.setAskSize(iexQuote.getIexAskSize());
        return quote;
    }

    public void updateMarketData() {
        List<Quote> quoteList = findAllQuotes();
        for(Quote quoteIterator : quoteList) {
            IexQuote iexQuote = findIexQuoteByTicker(quoteIterator.getId());
            Quote quote = buildQuoteFromIexQuote(iexQuote);
            saveQuote(quote);
        }
    }

    public List<Quote> findAllQuotes() {
        return (List<Quote>) quoteDao.findAll();
    }

    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }
}