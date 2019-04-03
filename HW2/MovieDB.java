import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.lang.model.util.ElementScanner6;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {
	MyLinkedList<Genre> genreList;

    public MovieDB() {
		genreList = new MyLinkedList<Genre>();
    }

    public void insert(MovieDBItem item) {
		Genre targetGenre = null;
		for(Genre g : genreList)
		{
			int compareResult = g.getItem().compareTo(item.getGenre());
			if (compareResult < 0)
			{
				continue;
			}
			else if (compareResult == 0)
			{
				targetGenre = g;
				break;
			}
			else
			{
				genreList.insertAt(new Genre(item.getGenre()), g);
			}
		}
		if(targetGenre == null)
		{
			genreList.add((targetGenre = new Genre(item.getGenre())));
		}

		targetGenre.getList().add(item.getTitle());
    }

    public void delete(MovieDBItem item) {
		Genre targetGenre = null;
		for(Genre g : genreList)
		{
			int compareResult = g.getItem().compareTo(item.getGenre());
			if (compareResult < 0)
			{
				continue;
			}
			else if (compareResult == 0)
			{
				targetGenre = g;
				break;
			}
			else
			{
				return;
			}
		}
		if(targetGenre == null)
		{
			return;
		}

		var list = targetGenre.getList();
		list.remove(item.getTitle());

		if(targetGenre.getList().size() == 0)
		{
			genreList.remove(targetGenre);
		}
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.
    	
    	// Printing search results is the responsibility of SearchCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
    	
        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
    	System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);
    	
    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();

        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
		var results = new MyLinkedList<MovieDBItem>();

		for(var g: genreList)
		{
			for(var m: g.movieList)
			{
				results.add(new MovieDBItem(g.getItem(), m));
			}
		}
    	return results;
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
	MovieList movieList;
	public Genre(String name) {
		super(name);
		movieList = new MovieList();
	}
	
	@Override
	public int compareTo(Genre o) {
		return this.getItem().compareTo(o.getItem());
	}

	@Override
	public int hashCode() {
		return this.getItem().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.getItem().equals(obj);
	}

	public MovieList getList()
	{
		return movieList;
	}
}

class MovieList implements ListInterface<String> {	
	MyLinkedList<String> movieList;
	public MovieList() {
		movieList = new MyLinkedList<String>();
	}

	@Override
	public Iterator<String> iterator() {
		return movieList.iterator();
	}

	@Override
	public boolean isEmpty() {
		return movieList.isEmpty();
	}

	@Override
	public int size() {
		return movieList.size();
	}

	@Override
	public void add(String item) {
		boolean isDone = false;
		for(var i : movieList)
		{
			int comapreResult = i.compareTo(item);

			if(comapreResult < 0)
			{
				continue;
			}
			else if (comapreResult == 0)
			{
				isDone = true;
				return;
			}
			else
			{
				movieList.insertAt(item, i);
				isDone = true;
				break;
			}
		}
		if (!isDone)
		{
			movieList.add(item);
		}
	}

	public void remove(String item)
	{
		movieList.remove(item);
	}

	@Override
	public String first() {
		return movieList.head.getNext().getItem();
	}

	@Override
	public void removeAll() {
		movieList.removeAll();
	}
}