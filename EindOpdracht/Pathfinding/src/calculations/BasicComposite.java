package calculations;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Composite with no special Effects, other than a slight alphablending
 * Required to prevent lag caused by switching away from a Composite (and not by switching towards one)
 */

public class BasicComposite implements Composite {
    @Override
    public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
        return new CompositeContext() {
            @Override
            public void dispose() {

            }


            @Override
            public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {

                //Samples per pixel (?)
                int chan1 = src.getNumBands();
                int chan2 = dstIn.getNumBands();
                int minCh = Math.min(chan1, chan2);

                //Loop through pixels of the shape
                for (int x = 0; x < dstIn.getWidth(); x++) {
                    for (int y = 0; y < dstIn.getHeight(); y++) {

                        float[] pxSrc = null;
                        pxSrc = src.getPixel(x, y, pxSrc);

                        float alpha = 190;
                        for (int i = 0; i < 3 && i < minCh; i++) {
                            pxSrc[i] = Math.min(255, (pxSrc[i] * (alpha / 255)));

                        }
                        dstOut.setPixel(x, y, pxSrc); //Why not outside loop

                    }
                }

            }
        };
    }
}
