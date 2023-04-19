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
                .tokenizer( NGramTokenizerFactory.class )
                .param( "minGramSize", "3" )
                .param( "maxGramSize", "15" )
                .tokenFilter(LowerCaseFilterFactory.class);

        context.analyzer("query").custom()
                .tokenizer(StandardTokenizerFactory.class)
                .tokenFilter(LowerCaseFilterFactory.class);

        context.normalizer("lowercase").custom()
                .tokenFilter(LowerCaseFilterFactory.class);
    }
}
