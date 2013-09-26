package zelda.game;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

public class Link {
	Texture sheet; 
	// Imagem com os frames do personagem
	
	TextureRegion standLeft, standRight, standUp, standDown, currentFrame;
	// Regiões da imagem referente aos frames
	
	Animation walkLeft, walkRight, walkUp, walkDown, currentAnimation;
	// Animações
	
	Rectangle form;
	/* O objeto rectangle neste exemplo foi utilizado 
	 * para guardar as coordenadas x e y do personagem
	 */
	
	float stateTime;
	/* Numero de segundos passados ​​desde o início da animação. 
	 * Isto é usado para determinar o estado da animação.
	 */
	
	boolean moving, left, right, up, down;
	/* Indicam se o personagem está se movendo e a direção que
	 * ele está encarando.
	 */
	
	TiledMapTileLayer collisionLayer;
	// Camada onde se encontram os tiles que o personagem deve colidir.
	
	String blocked;
	
	public Link(TiledMapTileLayer collisionLayer){
		this.collisionLayer = collisionLayer;
		blocked = "blocked";
		
		sheet = new Texture(Gdx.files.internal("res"+File.separator+"link.png"));
		// Carrega o arquivo de imagem com os frames.
		
		TextureRegion[][] aux = TextureRegion.split(sheet, 32, 42);
		/* O método split recebe o sheet e retorna um array bidimensional 
		 * com regiões de 32x42 pixels da textura que foi passada como
		 * parâmetro.
		 */
		
		TextureRegion[] left, right, up, down;
		
		left = new TextureRegion[7];
		right = new TextureRegion[7];
		up = new TextureRegion[7];
		down = new TextureRegion[7];
		/* Criando arrays auxiliares que serão preenchidos
		 * com os frames das animações.
		 */
		
		for(int i=0; i<7; i++){
			down[i] = aux[0][i];
			up[i] = aux[1][i];
			left[i] = aux[2][i];
			right[i] = aux[3][i];
		}
		/* Os frames estão organizados na imagem em linhas e
		 * colunas. Cada linha contém os frames de uma animação.
		 */
		
		standLeft = aux[2][0];
		standRight = aux[3][0];
		standUp = aux[1][0];
		standDown = aux[0][0];
		currentFrame = standDown;
		/* O primeiro frame de cada linha representa o
		 * personagem parado em uma direção.
		 */
		
		walkLeft = new Animation(0.07f, left);
		walkRight = new Animation(0.07f, right);
		walkUp = new Animation(0.07f, up);
		walkDown = new Animation(0.07f, down);
		/* Criando as animações. O primeiro parâmetro é o tempo
		 * que cada frame ficará na tela em segundos.*/
		stateTime = 0;
		
		form = new Rectangle();
		form.height = 42;
		form.width = 32;
		form.x = (640/2)-(32/2);
		form.y = (640/2)-(32/2);
		/* Cria um Rectangle com as mesmas dimensões dos frames e
		 * o posiciona no meio da tela.
		 */
	}
	
	public void update(){
		stateTime += Gdx.graphics.getDeltaTime();
		/* Adiciona o tempo decorrido desde a última 
		 * renderização para o stateTime.
		 */
	}
	
	public TextureRegion currentFrame(){
		if(moving){
			return currentAnimation.getKeyFrame(stateTime, true);
			/* Caso o personagem esteja em movimento, retorna o frame
			 * da animação no dado tempo. O segundo parâmetro indica
			 * se animação está em looping.
			 */
		}
		else return currentFrame;
		// Caso contrário, retorna o frame atual.
	}
	
	public void move(){
		int x = (int) getX(), y = (int) getY();
		// Guardando as coordenadas iniciais
		
		if(Gdx.input.isKeyPressed(Keys.A)){
			// Checa se a tecla foi pressionada
			
			left = true;
			/* Marca como verdadeira a dirêção referênte 
			 * a tecla pressionada
			 */
			
			form.x -= 4;
			// Atualiza as coordenadas
			
			currentAnimation = walkLeft;
			/* Determina a animação atual de acordo a
			 * direção em que o presonagem se moverá
			 */
		}
		else if(Gdx.input.isKeyPressed(Keys.D)){
			right = true;
			form.x += 4;
			currentAnimation = walkRight;
		}
		else{
			if(left) currentFrame = standLeft;
			else if(right) currentFrame = standRight;
			left = false;
			right = false;
		}
		
		if(Gdx.input.isKeyPressed(Keys.S)){
			down = true;
			form.y -= 4;
			currentAnimation = walkDown;
		}
		else if(Gdx.input.isKeyPressed(Keys.W)){
			up = true;
			form.y += 4;
			currentAnimation = walkUp;
		}
		else{
			if(up) currentFrame = standUp;
			else if(down) currentFrame = standDown;
			up = false;
			down = false;
		}
		
		moving = (left || right) || (up || down);
		// Verifica se o presonagem está se movendo em qualquer direção
		
		/* Checa se a celula nas seguntes coordenadas está bloqueada.
		 * O método getCell() retorna a célula nas coordenadas dadas, 
		 * como uma matriz. Existe uma celula para cada tile. Temos um
		 * Tile Map de 40x40 tiles e cada tile mede 16x16 pixels. A 
		 * posição do tile onde o personagem se encontra é dada pela 
		 * posição x e y do personagem dividida por 16.
		 */
		if(isCellBlocked(collisionLayer.getCell(((int)(getX()+12)/16), ((int)(getY()+12)/16)))){
			/* Caso a célula esteja bloqueada, o personagem volta para
			 * a posição anterior. Isso acontece antes do personagem ser
			 * desenhado na tela.
			 */
			form.x = x;
			form.y = y;
		}
	}
	
	public float getX(){
		return form.x;
	}
	
	public float getY(){
		return form.y;
	}
	
	public boolean isCellBlocked(Cell cell){
		if(cell == null) return false;
		return cell.getTile().getProperties().containsKey(blocked);
		// Retorna true se a celula dada comtém a chave de bloqueio definida
	}
}
