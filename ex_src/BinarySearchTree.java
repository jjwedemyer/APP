package app.exercise.adt;

import java.util.ArrayList;
import java.util.Collections;

public class BinarySearchTree {
  //Eigenschaften des repräsentierten Knotens
  private BinarySearchTree parent = null;
  private BinarySearchTree links = null;
  private BinarySearchTree rechts = null;
  private Comparable element;

  //Konstruktor
  public BinarySearchTree(BinarySearchTree parent, Comparable element) {
    this.parent = parent;
    this.element = element;
  }

  public void insert(Comparable number) {
    BinarySearchTree current = this;
    //Wurzel finden
    while(current.getParent() != null) {
      current = current.getParent();
    }
    if(number.compareTo(current.getElement()) < 0){
      //number ist kleiner als ELement
      while(current.getLinks() != null) {
        //Finde Knoten, bei dem linker Ast noch frei ist
        current = current.getLinks();
      }
      current.setLinks(new BinarySearchTree(current, number));
    } else if(number.compareTo(current.getElement()) > 0) {
      //number ist größer als Element
      while(current.getRechts() != null) {
        //Finde Knoten, bei dem rechter Ast noch frei ist
        current = current.getRechts();
      }
      current.setRechts(new BinarySearchTree(current, number));
    } else {
      throw new RuntimeException("Element bereits im Suchbaum enthalten");
    }
  }
  public String toString() {
    ArrayList<Comparable> list = new ArrayList<Comparable>();
    //Wurzel finden
    BinarySearchTree root = this;
    while(root.getParent() != null) {
      root = root.getParent();
    }
    //Rekursiv Suchbaum durchlaufen
    list.add(recurString(root, list));
    //Aufsteigend sortieren
    Collections.sort(list);
    //Zurückgeben
    String ret = "";
    for(Comparable current: list){
  		ret += current.toString() + " ";
  	}
    return ret;
  }
  private static Comparable recurString(BinarySearchTree current, ArrayList<Comparable> list) {
     if(current.getLinks() != null) {
       list.add(recurString(current.getLinks(), list));
     }
     if(current.getRechts() != null) {
       list.add(recurString(current.getLinks(), list));
     }
     return current.getElement();
  }
  //Getter
  public BinarySearchTree getParent() {
    return this.parent;
  }
  public BinarySearchTree getLinks() {
    return this.links;
  }
  public BinarySearchTree getRechts() {
    return this.rechts;
  }
  public Comparable getElement() {
    return this.element;
  }
  //Setter
  public void setLinks(BinarySearchTree newlinks) {
    this.links = newlinks;
  }
  public void setRechts(BinarySearchTree newrechts) {
    this.links = newrechts;
  }
}
