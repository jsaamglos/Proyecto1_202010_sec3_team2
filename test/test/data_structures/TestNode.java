package test.data_structures;

import model.data_structures.Node;

import static org.junit.Assert.*;

/*
import org.junit.Before;
import org.junit.Test;
*/

public class TestNode
{
    private Node<String> nodo;

    public TestNode()
    {
        pruebaCaso1();
        pruebaCaso2();
    }

    public void  setUp1()
    {
        nodo = new Node<String>("Test1", null);
    }

    public void setUp2()
    {
        nodo = new Node<String>("Test2", new Node<String>("anterior", null));
        new Node<String>("siguiente", nodo);
    }

    public void pruebaCaso1()
    {
        setUp1();

        assertEquals(nodo.getElemento(), "Test1");
    }

    public void pruebaCaso2()
    {
        setUp2();

        assertEquals(nodo.getAnterior().getElemento(), "anterior");
        assertEquals(nodo.getSiguiente().getElemento(), "siguiente");
    }

}