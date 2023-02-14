package com.makalu.hrm.config;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramTokenizerFactory;
import org.apache.lucene.analysis.shingle.ShingleFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;

public class MakaluLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {

    @Override
    public void configure(LuceneAnalysisConfigurationContext context) {

        context.analyzer("ngram").custom()
                .tokenizer( StandardTokenizerFactory.class )
                .tokenFilter( LowerCaseFilterFactory.class )
                .tokenFilter( ShingleFilterFactory.class )
                .param( "maxShingleSize", "2" )
                .param( "tokenSeparator", " " )
                .tokenFilter(NGramFilterFactory.class)
                .param( "minGramSize", "1" )
                .param( "maxGramSize", "15" );

        context.analyzer("query").custom()
                .tokenizer(StandardTokenizerFactory.class)
                .tokenFilter(LowerCaseFilterFactory.class);

        context.normalizer("lowercase").custom()
                .tokenFilter(LowerCaseFilterFactory.class);
    }
}
