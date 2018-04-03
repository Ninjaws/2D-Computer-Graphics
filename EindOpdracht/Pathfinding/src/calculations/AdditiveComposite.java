package calculations;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Composite with an additive effect (colors are combined)
 */

public class AdditiveComposite implements Composite {
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
                        float[] pxDst = null;
                        pxDst = dstIn.getPixel(x, y, pxDst);

                        float alpha = 255;
                        //Allows images to use the composite
                        if (pxSrc.length > 3) {
                            alpha = pxSrc[3];
                        }

                        for (int i = 0; i < 3 && i < minCh; i++) {
                            pxDst[i] = Math.min(255, (pxSrc[i] * (alpha / 255)) + (pxDst[i]));
                            dstOut.setPixel(x, y, pxDst); //Why not outside loop
                        }
                    }
                }
            }
        };
    }
}
