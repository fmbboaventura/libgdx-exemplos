package zelda.game;

import java.io.File;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class ZeldaGame implements ApplicationListener {
	
	OrthographicCamera camera;
	// Camera de projeção ortográfica
	
	SpriteBatch batch;
	// Objeto responsável por desenhar texturas na tela
	
	TiledMap map;
	// Mapa de tiles
	
	OrthogonalTiledMapRenderer renderer;
	// Objeto resposável por renderizar o mapa
	
	Link link;

	@Override
	public void create() {
		float unitScale = 1/32f;
		/* O uinitScale diz ao renderizador quantos pixels
		 * representam uma unidade do mapa. Neste caso, 
		 * 1 unidade é igual a 32 pixels.
		 */
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 20, 20);
		/* Criando e configurando a câmera para operar
		 * na unitSacle definida anteriormente. Neste 
		 * caso, a camera irá renderizar 20x20 unidades
		 * do mapa.
		 */
		
		batch = new SpriteBatch();
		
		map = new TmxMapLoader().load("res"+File.separator+"hyrule.tmx");
		/* Carrega o tile map criado no tiled map editor.
		 * Dimensões do mapa: 20x20 tiles
		 * Dimensões dos tiles: 32x32 pixels
		 */
		
		renderer = new OrthogonalTiledMapRenderer(map, unitScale);
		renderer.setView(camera);
		// Cria o renderizador de mapas e seta seu campo de visão.
		
		link = new Link();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// Limpa a tela
		
		renderer.render();
		// Desenha o mapa
		
		link.update();
		// Atualiza o stateTime de Link
		
		batch.begin();
		// Inicia o batch
		
		batch.draw(link.currentFrame(), link.getX(), link.getY());
		// Desenha Link nas suas coordenadas atuais.
		
		batch.end();
		// Encerra o batch
		
		link.move();
		// Método que atualiza as coordenadas de Link
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
