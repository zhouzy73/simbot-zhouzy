package love.simbot.example.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

public class PixivDownloadUtil {

	public void downloadPicture(String url, File file) throws ClientProtocolException, IOException, InterruptedException {
		PivixHttpClientUtil hcu = PivixHttpClientUtil.getInstance();
		CloseableHttpResponse response = hcu.doGet(url);
		HttpEntity entity = response.getEntity();
		byte[] picture = EntityUtils.toByteArray(entity);
		byte[] rotatePic = rotatePicture(picture);
		savePicture(rotatePic, file);
	}

	/**
	 * @apiNote: 将图片顺时针旋转90°
	 * @param: byte[] imgByte 图片字节数组
	 * @return: byte[] 图片字节数组
	 * @author: created by zhouzy on 2022/8/29.
	 * @author: last modified by zhouzy on 2022/8/29.
	 */
	public byte[] rotatePicture(byte[] picture) {
		//获取输入字节流
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(picture);
		//将byte数组转换为image对象  访问图像缓冲区的图像对象
		BufferedImage bufferedImage;
		try {
			//获得图片对象
			bufferedImage = ImageIO.read(byteArrayInputStream);
			//获得当前的图片的高度和宽度
			int thisHeight = bufferedImage.getHeight();
			int thisWidth = bufferedImage.getWidth();
			BufferedImage dstImage = null;
			//创建图片矩阵向量旋转对象类表示
			//2D 仿射变换，它执行从 2D 坐标到其他 2D 坐标的线性映射，保留了线的“直线性”和“平行性”。可以使用一系列平移、缩放、翻转、旋转和剪切来构造仿射变换。
			AffineTransform affineTransform = new AffineTransform();
			//设置图像平移的距离
			//thisHeight - 坐标在 X 轴方向上平移的距离
			//0 - 坐标在 Y 轴方向上平移的距离
			affineTransform.translate(thisHeight, 0);
			//创建一个新的画布
			//旋转后  thisHeight图像的宽度  thisWidth 图像的高度
			dstImage = new BufferedImage(thisHeight, thisWidth, bufferedImage.getType());
			//设置图片旋转的角度  是X轴正半轴 向Y轴正半轴 旋转的角度
			affineTransform.rotate(java.lang.Math.toRadians(90));
			//执行源图像的线性2D映射
			AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, null);
			//转换图片对象为bufferedImage对象
			affineTransformOp.filter(bufferedImage, dstImage);
			// 创建字节输入流
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(dstImage, "JPEG", byteArrayOutputStream);
			//获取新图片的数组
			byte[] newImg = byteArrayOutputStream.toByteArray();
			return newImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return picture;
	}

	public void savePicture(byte[] picture, File file) {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(picture);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

}
