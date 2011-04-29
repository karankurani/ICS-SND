package edu.cornell.ics;

import edu.cornell.ics.entities.Author;
import edu.cornell.ics.entities.Query;
import edu.cornell.ics.interfaces.IQuery;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.cornell.ics.utilities.*;
import edu.cornell.ics.utilities.providers.EntryProvider;
import edu.cornell.ics.utilities.providers.HibernateDataProvider;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.Ignore;

public class CoAuthorTest {

    private static final Logger log = Logger.getLogger(AuthorTest.class);    
    private static HibernateDataProvider<Author> authorProvider;

    // Uncomment this if you want ton run these tests.
    @BeforeClass
    public static void setup() {
        authorProvider = new HibernateDataProvider<Author>();
    }

    // Uncomment this if you want ton run these tests.
    @AfterClass
    public static void cleanup() {
    }

    @Test
//    @Ignore
    public void CoAuthorInsert() {
        IterableFile ifile = new IterableFile(UnitTests.DATA_PATH + "CoAuthorNetwork.txt");
        String line;
        String splitS[];
        String author;
        String[] coAuthors;
        Iterator<String> iterator = ifile.iterator();
        while(iterator.hasNext()){
            line = iterator.next();
            System.out.println(line);
            splitS = line.split("<break>");
            author = splitS[0];
            coAuthors = splitS[1].split(",");
            Set hashSet = new HashSet(Arrays.asList(coAuthors));
            coAuthors = (String[]) hashSet.toArray(new String[hashSet.size()]);
            IQuery qry = new Query("from Author where authorId=:authorId");
            qry.setParameter("authorId", Integer.parseInt(author));
            Author a = authorProvider.Load(qry);            
            for(String coAuthor : coAuthors){
                qry.setParameter("authorId", Integer.parseInt(coAuthor));
                Author co = authorProvider.Load(qry);
                a.addCoAuthor(co);
            }
            authorProvider.Update(a);
        }
    }
}
