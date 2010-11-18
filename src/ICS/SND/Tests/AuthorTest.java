package ICS.SND.Tests;

import java.util.*;

import org.junit.Test;

import ICS.SND.Entities.Author;
import ICS.SND.Entities.Entry;
import ICS.SND.Entities.Query;
import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IQuery;
import ICS.SND.Utilities.Providers.HibernateDataProvider;

public class AuthorTest {
    
    @Test
    public void createAuthor(){
         
        IDataProvider<Entry> entryProvider = new HibernateDataProvider<Entry>();
        IDataProvider<Author> authorProvider = new HibernateDataProvider<Author>();
        
        for(int i=681946;i<1632444;i++){
            IQuery q = new Query("from Entry where indexNumber = '"+ i +"'");
            Entry e = (Entry) entryProvider.Load(q);
            if(e!=null){
                String[] splits = e.getAuthor().split(",");
                System.out.println(e.getIndexNumber() + " " + e.getAuthor());
                Set<Author> aSet = new HashSet<Author>();
                
                for(String author : splits){
//                    System.out.println(author);
                    author = author.trim();
                    q = new Query("from Author where authorName = :author");
                    q.setParameter("author", author);
                    Author existingAuthor = (Author) authorProvider.Load(q);
                    if(existingAuthor==null){
                        Author a = new Author();
                        a.setAuthorName(author);
                        authorProvider.Save(a);
                        aSet.add(a);
                    }
                    else{
                        boolean val=true;
                        Iterator<Author> iter = aSet.iterator();
                        while(iter.hasNext()){
                            Author tempA = (Author) iter.next();
                            if(tempA.getAuthorName().equals(existingAuthor.getAuthorName())){
                                val = false;
                                break;
                            }
                        }
                        
                        if(val){
                            aSet.add(existingAuthor);
                        }
                    }
                }     
                e.setAuthors(aSet);
                entryProvider.Update(e);
            }
            else{
                System.out.println("Entry with indexNumber " + i + " does not exist");
            }
        }
     
    }
}
