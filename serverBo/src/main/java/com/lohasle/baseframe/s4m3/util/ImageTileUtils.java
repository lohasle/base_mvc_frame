package com.lohasle.baseframe.s4m3.util;

import com.lohasle.baseframe.s4m3.constant.Constant;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @版权：汇锦软件 版权所有 (c) 2013
 * @author wuxin
 * @version Revision 1.0.0
 * @see:
 * @创建日期：2013-11-11
 * @功能说明：
 *         图片切割
 *         1.图片切割1-5个等级
 *         2.每个最小化图片大小:256*256
 *
 */
public class ImageTileUtils {	
	

	////图片切割最小等级
	public static final int MINLEVEL = 1;
	//图片切割最大等级
	private static final int MAXLEVEL = 5;
	//图片所在等级
	private static final int picLevel =5;
	//定义最小图块大小
    private static final double MINWH = 256;
    
	private String picPath;//要切割图片的位置
    private String savePath;//切割图片保存的位置
    
	public ImageTileUtils(){
		
	}
	
	public ImageTileUtils(String picPath,String savePath){
		this.picPath = picPath;
		this.savePath = savePath;
	}
	
	/**
	 * 
	 * @功能说明:
	 *         切割5个等级的图片
	 * .
	 * @author wuxin
	 * 2013-11-11 wuxin
	 * @throws java.io.IOException
	 */
	public void cutImage() throws IOException {
		//读取源图片
		BufferedImage bi = ImageIO.read(new File(picPath));
		//源图片宽度
		int width = bi.getWidth();
		//源图片高度
	    int height = bi.getHeight();
	    //切割每一级
		for(int i = MINLEVEL; i <= MAXLEVEL; i++){
			int level = i;
			//创建切图保存路径
		    String dirName = createSavePath(level);
		    //缩放后的图片宽度(当前图片)
		    double sWidth = width * Math.pow(2, level - picLevel);
		    //缩放后的图片高度
		    double sHeight = height * Math.pow(2, level - picLevel);
		    //图片存放画布宽度
		    int extendWidth = (int)((Math.ceil(sWidth / MINWH)+1) * MINWH);
		    //图片存放画布的高度
		    int extendHeight = (int)((Math.ceil(sHeight / MINWH)+1) * MINWH);
		    //当前图片位于画布左上角的x坐标
	        int remMinX = 256 - (int) (sWidth/2) % 256;
	        //当前图片位于画布左上角的y坐标
	        int remMaxY = 256 - (int) (sHeight/2) % 256;
	        //x轴最小图块
	        int neatMinX = -(extendWidth/256/2);
	        //x轴最大图块
		    int neatMinY = -(extendHeight/256/2);
		    //y轴最小图块
		    int neatMaxX = extendWidth/256/2 -1;
		    //y轴最大图块
		    int neatMaxY = extendHeight/256/2 -1;
		     
		    //创建画布
	        BufferedImage outputImage = null;
	        Graphics2D g = bi.createGraphics();
	        BufferedImage extend = g.getDeviceConfiguration().createCompatibleImage(extendWidth, extendHeight, Transparency.TRANSLUCENT);
	        g.dispose();
	        g = extend.createGraphics();
	        //把缩放后的图片画于画布上
	        g.drawImage(bi, remMinX, remMaxY, (int)sWidth, (int)sHeight, null);
	        outputImage = extend;
	        
	        //得到新的图片
		    Image image = extend.getScaledInstance(extendWidth, extendHeight, Image.SCALE_DEFAULT);
		    
		    //x轴总图块大小
		    int w = neatMaxX - neatMinX + 1;
		    //y轴总图块大小
		    int h = neatMaxY - neatMinY + 1;
		    
		    for(int m = 0; m < w; m++) {
		           for(int n = 0; n < h; n++) {
		        	   //以256*256切割新的图片
		        	   ImageFilter cropFilter = new CropImageFilter(256 * m, 256 * n, 256, 256);
		        	   Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(),cropFilter));
		               BufferedImage tag = new BufferedImage(256, 256 , BufferedImage.TYPE_INT_BGR);
		               Graphics2D gs = tag.createGraphics();
		               tag = gs.getDeviceConfiguration().createCompatibleImage(256, 256, Transparency.TRANSLUCENT);
		               gs.dispose();
		               gs = tag.createGraphics();
		               gs.drawImage(img, 0, 0, null);
		               g.dispose();
		               String cropPicName = dirName + "/tile" + (neatMinX + m) + "_" + -(neatMinY + n +1) + ".png";
		               ImageIO.write(tag, "png", new File(cropPicName));
		           }
		    }
		}
	}
	
	/**
	 * 
	 * @功能说明: 创建切图保存路径
	 * .
	 * @author wuxin
	 * 2013-11-11 wuxin
	 */
	public String createSavePath(int level){
		//创建路径
        String dirName = savePath.substring(0, savePath.lastIndexOf("\\")) + "\\tiles\\" + level;
	    File dir = new File(dirName);
	    dir.mkdirs();
	    return dirName;
	}

    /**
    *
    * @功能说明: 创建缩略图用于手机端（300*150 会比较模糊，暂时先放大一倍）
    * .
    * @param path 图片源文件相对路径
    * @param name 图片源文件名称
    *
    * @author wuxin
    * 2013-11-11 wuxin
    */
	public static void creatThumbPicture(String path,String name){
            String thumbSourceDir = System.getProperty(Constant.WORKDIR) + path ;
            String newPath = System.getProperty(Constant.WORKDIR) + "_thumbs/" + path;
            File file = new File(newPath);
            if(!file.exists()){
                    file.mkdirs();
            }
            try {
                    String path1 = (thumbSourceDir + "/" + name).replace("/","\\");
                    String path2 = (newPath + "/" + name).replace("/","\\");
                    Thumbnails.of(path1).size(600,300).keepAspectRatio(false).outputQuality(0.8f).toFile(path2);
                    //Thumbnails.of(path2).scale(0.5f).toFile(path2);
            } catch (IOException e) {
                    e.printStackTrace();
            }
    }

}
