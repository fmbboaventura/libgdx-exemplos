package zelda.game;

import java.io.File;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class ZeldaGame implements ApplicationListener {
	
	OrthographicCamera camera;
	// Camera de proje��o ortogr�fica
	
	SpriteBatch batch;
	// Objeto respons�vel por desenhar texturas na tela
	
	TiledMap map;
	// Mapa de tiles
	
	OrthogonalTiledMapRenderer renderer;
	// Objeto respos�vel por renderizar o mapa
	
	Link link;
	
	Music theme;
	// Musica de fundo

	@Override
	public void create() {
		theme = Gdx.audio.newMusic(Gdx.files.internal("res"+File.separator+"ZeldaMainTheme.mp3"));
		// Carrega a musica.
		
		theme.setLooping(true);
		// Define se a musica deve recome�ar quando chega ao fim.
		
		theme.play();
		float unitScale = 1/16f;
		/* O uinitScale diz ao renderizador quantos pixels
		 * representam uma unidade do mapa. Neste caso, 
		 * 1 unidade � igual a 32 pixels.
		 */
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 40, 40);
		/* Criando e configurando a c�mera para operar
		 * na unitSacle definida anteriormente. Neste 
		 * caso, a camera ir� renderizar 20x20 unidades
		 * do mapa.
		 */
		//camera.zoom = 100f;
		//camera.update();
		
		batch = new SpriteBatch();
		
		map = new TmxMapLoader().load("res"+File.separator+"hyrule2.tmx");
		/* Carrega o tile map criado no tiled map editor.
		 * Dimens�es do mapa: 20x20 tiles
		 * Dimens�es dos tiles: 32x32 pixels
		 */
		
		renderer = new OrthogonalTiledMapRenderer(map, unitScale);
		renderer.setView(camera);
		// Cria o renderizador de mapas e seta seu campo de vis�o.
		
		// Cria o link passando a camada de colis�o
		link = new Link((TiledMapTileLayer) map.getLayers().get(1));
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
		// M�todo que atualiza as coordenadas de Link
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
