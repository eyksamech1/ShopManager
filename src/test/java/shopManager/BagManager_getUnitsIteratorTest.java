package shopManager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import exceptions.NoEnoughStock;
import exceptions.NotInStock;
import model.Order;
import model.Product;
import persistency.OrderRepository;
import shopmanager.MyBagManager;
import shopmanager.StockManager;

class BagManager_getUnitsIteratorTest 
{

	private static Logger trazador=Logger.getLogger(ProductTest.class.getName());
	
	//Creo los objetos sustitutos (representantes o mocks)
	//Son objetos contenidos en MyBagManager de los que aún no disponemos el código
	@Mock(serializable = true)
	private static Product producto1Mock= Mockito.mock(Product.class);
	@Mock(serializable = true)
	private static Product producto2Mock= Mockito.mock(Product.class);
	@Mock(serializable = true)
	private static Product producto3Mock= Mockito.mock(Product.class);
	@Mock
	private static StockManager stockMock= Mockito.mock(StockManager.class);
	@Mock 
	private static OrderRepository repositoryMock= Mockito.mock(OrderRepository.class);
	@Mock
	private static Order orderMock=Mockito.mock(Order.class);
	
	//Inyección de dependencias
	//Los objetos contenidos en micestaTesteada son reemplazados automáticamente por los sustitutos (mocks)
	@InjectMocks
	private static MyBagManager micestaTesteada;
	
	
	@BeforeEach
	void setUpBeforeClass()	
	{
		//Todos los tests empiezan con la bolsa vacía
		 micestaTesteada.reset();
	}
	
	@Test
	@Tag("unidad")
	@DisplayName("Prueba")
    void test_iterador_mas_menos() throws NoEnoughStock, NotInStock 
	{
		//Inicializo los mocks
		Mockito.when(producto1Mock.getId()).thenReturn("id1");
		Mockito.when(producto1Mock.getNumber()).thenReturn(1);
		Mockito.when(producto2Mock.getId()).thenReturn("id2");
		Mockito.when(producto2Mock.getNumber()).thenReturn(2);
		Mockito.when(producto3Mock.getId()).thenReturn("id3");
		Mockito.when(producto3Mock.getNumber()).thenReturn(3);	
		//Agrego los productos a la cesta considerando que se añadieron correctamente
		micestaTesteada.addProduct(producto1Mock);
		micestaTesteada.addProduct(producto2Mock);		
		micestaTesteada.addProduct(producto3Mock);
		Iterator<Product> iterador=micestaTesteada.getUnitsIterator();
		//Pruebo que no esta vacia
		if (!iterador.hasNext()) // Si no tiene siguiente, es porque esta vacia
		{
			fail("El iterador fue recibido vacio");
		}
		//Comprobamos que esten en orden de mayor a menor.
		if (!(producto3Mock.getNumber()==iterador.next().getNumber()))
		{
			fail("El iterador no está ordenando correctamente (posicion1)");			
		}
		if (!iterador.hasNext()) // Si no tiene siguiente, es porque tiene un solo producto
		{
			fail("No se tienen todos los elementos que se metieron en la cesta");
		}
		if (!(producto2Mock.getNumber()==iterador.next().getNumber()))
		{
			fail("El iterador no está ordenando correctamente (posicion2)");			
		}
		if (!iterador.hasNext()) // Si no tiene siguiente, es porque tiene dos productos
		{
			fail("No se tienen todos los elementos que se metieron en la cesta");
		}
		if (!(producto1Mock.getNumber()==iterador.next().getNumber()))
		{
			fail("El iterador no está ordenando correctamente (posicion3)");			
		}
		if (iterador.hasNext()) // Si tiene siguiente, es porque tiene productos demas
		{
			fail("La cesta tiene mas productos de lo esperado");
		}
		//fail("Not yet implemented");
	}

}
