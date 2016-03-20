package com.example.valentin.cartoonfilter.CustomViews;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.valentin.cartoonfilter.MainActivity;
import com.example.valentin.cartoonfilter.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage3x3ConvolutionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDirectionalSobelEdgeDetectionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHalftoneFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLaplacianFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNonMaximumSuppressionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSmoothToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageSphereRefractionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWeakPixelInclusionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;


/**
 * Created by Valentin on 03/11/2015.
 */
public class SlideMenu {

    DrawerLayout menuLayout; //Layout Principal
    ListView menuElementsList; //Menu
    CustomAdapter customAdapter;
    List<ListViewItem> userList;
    GPUImageView mView;
    FilterList mList;

    public SlideMenu(final MainActivity activity, final GPUImageView view) {

        menuLayout = (DrawerLayout) activity.findViewById(R.id.menu_layout);
        menuElementsList = (ListView) activity.findViewById(R.id.menu_elements);
        mView = view;
        mList = new FilterList();
        mList = setFilterList();

        Log.i("Debug", "menuLayout = " + menuLayout);

        if (menuLayout != null && menuElementsList != null) {

            menuLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
            userList = new ArrayList<>();

            customAdapter = new CustomAdapter(activity, R.id.textModule, userList);
            customAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            menuElementsList.setAdapter(customAdapter);
            menuElementsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                    TextView textView = (TextView) view.findViewById(R.id.textModule);
                    String text = textView.getText().toString();

                    GPUImageFilter mFilter = createFilterForType(activity.getApplicationContext(), mList.filters.get(mList.names.indexOf(text)));

                    mView.setFilter(mFilter);
                    mView.requestRender();
                    menuLayout.closeDrawers();
                }
            });
        }
    }

    public void generateBaseMenu() {
        userList.clear();
        userList.add(new ListViewItem("Contrast", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Invert", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Pixelation", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Hue", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Gamma", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Brightness", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Sepia", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Grayscale", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Sharpness", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Sobel Edge Detection", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("3x3 Convolution", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Emboss", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Posterize", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Grouped filters", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Saturation", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Exposure", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Highlight Shadow", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Monochrome", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Opacity", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("RGB", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("White Balance", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Vignette", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Gaussian Blur", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Crosshatch", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Box Blur", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("CGA Color Space", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Dilation", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Kuwahara", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("RGB Dilation", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Sketch", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Toon", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Smooth Toon", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Halftone", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Bulge Distortion", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Glass Sphere", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Haze", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Laplacian", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Non Maximum Suppression", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Sphere Refraction", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Swirl", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Weak Pixel Inclusion", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("False Color", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Color Balance", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Levels Min (Mid Adjust)", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Bilateral Blur", CustomAdapter.MENU_ITEM));
        userList.add(new ListViewItem("Transform (2-D)", CustomAdapter.MENU_ITEM));
        customAdapter.notifyDataSetChanged();
    }


    private static GPUImageFilter createFilterForType(final Context context, final FilterType type) {
        switch (type) {
            case CONTRAST:
                return new GPUImageContrastFilter(2.0f);
            case GAMMA:
                return new GPUImageGammaFilter(2.0f);
            case INVERT:
                return new GPUImageColorInvertFilter();
            case PIXELATION:
                return new GPUImagePixelationFilter();
            case HUE:
                return new GPUImageHueFilter(90.0f);
            case BRIGHTNESS:
                return new GPUImageBrightnessFilter(1.5f);
            case GRAYSCALE:
                return new GPUImageGrayscaleFilter();
            case SEPIA:
                return new GPUImageSepiaFilter();
            case SHARPEN:
                GPUImageSharpenFilter sharpness = new GPUImageSharpenFilter();
                sharpness.setSharpness(2.0f);
                return sharpness;
            case SOBEL_EDGE_DETECTION:
                return new GPUImageSobelEdgeDetection();
            case THREE_X_THREE_CONVOLUTION:
                GPUImage3x3ConvolutionFilter convolution = new GPUImage3x3ConvolutionFilter();
                convolution.setConvolutionKernel(new float[]{
                        -1.0f, 0.0f, 1.0f,
                        -2.0f, 0.0f, 2.0f,
                        -1.0f, 0.0f, 1.0f
                });
                return convolution;
            case EMBOSS:
                return new GPUImageEmbossFilter();
            case POSTERIZE:
                return new GPUImagePosterizeFilter();
            case FILTER_GROUP:
                List<GPUImageFilter> filters = new LinkedList<GPUImageFilter>();
                filters.add(new GPUImageContrastFilter());
                filters.add(new GPUImageDirectionalSobelEdgeDetectionFilter());
                filters.add(new GPUImageGrayscaleFilter());
                return new GPUImageFilterGroup(filters);
            case SATURATION:
                return new GPUImageSaturationFilter(1.0f);
            case EXPOSURE:
                return new GPUImageExposureFilter(0.0f);
            case HIGHLIGHT_SHADOW:
                return new GPUImageHighlightShadowFilter(0.0f, 1.0f);
            case MONOCHROME:
                return new GPUImageMonochromeFilter(1.0f, new float[]{0.6f, 0.45f, 0.3f, 1.0f});
            case OPACITY:
                return new GPUImageOpacityFilter(1.0f);
            case RGB:
                return new GPUImageRGBFilter(1.0f, 1.0f, 1.0f);
            case WHITE_BALANCE:
                return new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
            case VIGNETTE:
                PointF centerPoint = new PointF();
                centerPoint.x = 0.5f;
                centerPoint.y = 0.5f;
                return new GPUImageVignetteFilter(centerPoint, new float[]{0.0f, 0.0f, 0.0f}, 0.3f, 0.75f);
            case GAUSSIAN_BLUR:
                return new GPUImageGaussianBlurFilter();
            case CROSSHATCH:
                return new GPUImageCrosshatchFilter();

            case BOX_BLUR:
                return new GPUImageBoxBlurFilter();
            case CGA_COLORSPACE:
                return new GPUImageCGAColorspaceFilter();
            case DILATION:
                return new GPUImageDilationFilter();
            case KUWAHARA:
                return new GPUImageKuwaharaFilter();
            case RGB_DILATION:
                return new GPUImageRGBDilationFilter();
            case SKETCH:
                return new GPUImageSketchFilter();
            case TOON:
                return new GPUImageToonFilter();
            case SMOOTH_TOON:
                return new GPUImageSmoothToonFilter();

            case BULGE_DISTORTION:
                return new GPUImageBulgeDistortionFilter();
            case GLASS_SPHERE:
                return new GPUImageGlassSphereFilter();
            case HAZE:
                return new GPUImageHazeFilter();
            case LAPLACIAN:
                return new GPUImageLaplacianFilter();
            case NON_MAXIMUM_SUPPRESSION:
                return new GPUImageNonMaximumSuppressionFilter();
            case SPHERE_REFRACTION:
                return new GPUImageSphereRefractionFilter();
            case SWIRL:
                return new GPUImageSwirlFilter();
            case WEAK_PIXEL_INCLUSION:
                return new GPUImageWeakPixelInclusionFilter();
            case FALSE_COLOR:
                return new GPUImageFalseColorFilter();
            case COLOR_BALANCE:
                return new GPUImageColorBalanceFilter();
            case LEVELS_FILTER_MIN:
                GPUImageLevelsFilter levelsFilter = new GPUImageLevelsFilter();
                levelsFilter.setMin(0.0f, 3.0f, 1.0f);
                return levelsFilter;
            case HALFTONE:
                return new GPUImageHalftoneFilter();

            case BILATERAL_BLUR:
                return new GPUImageBilateralFilter();

            case TRANSFORM2D:
                return new GPUImageTransformFilter();

            default:
                throw new IllegalStateException("No filter of that type!");
        }

    }

    private FilterList setFilterList() {

        final FilterList filters = new FilterList();
        filters.addFilter("Contrast", FilterType.CONTRAST);
        filters.addFilter("Invert", FilterType.INVERT);
        filters.addFilter("Pixelation", FilterType.PIXELATION);
        filters.addFilter("Hue", FilterType.HUE);
        filters.addFilter("Gamma", FilterType.GAMMA);
        filters.addFilter("Brightness", FilterType.BRIGHTNESS);
        filters.addFilter("Sepia", FilterType.SEPIA);
        filters.addFilter("Grayscale", FilterType.GRAYSCALE);
        filters.addFilter("Sharpness", FilterType.SHARPEN);
        filters.addFilter("Sobel Edge Detection", FilterType.SOBEL_EDGE_DETECTION);
        filters.addFilter("3x3 Convolution", FilterType.THREE_X_THREE_CONVOLUTION);
        filters.addFilter("Emboss", FilterType.EMBOSS);
        filters.addFilter("Posterize", FilterType.POSTERIZE);
        filters.addFilter("Grouped filters", FilterType.FILTER_GROUP);
        filters.addFilter("Saturation", FilterType.SATURATION);
        filters.addFilter("Exposure", FilterType.EXPOSURE);
        filters.addFilter("Highlight Shadow", FilterType.HIGHLIGHT_SHADOW);
        filters.addFilter("Monochrome", FilterType.MONOCHROME);
        filters.addFilter("Opacity", FilterType.OPACITY);
        filters.addFilter("RGB", FilterType.RGB);
        filters.addFilter("White Balance", FilterType.WHITE_BALANCE);
        filters.addFilter("Vignette", FilterType.VIGNETTE);
        filters.addFilter("Gaussian Blur", FilterType.GAUSSIAN_BLUR);
        filters.addFilter("Crosshatch", FilterType.CROSSHATCH);

        filters.addFilter("Box Blur", FilterType.BOX_BLUR);
        filters.addFilter("CGA Color Space", FilterType.CGA_COLORSPACE);
        filters.addFilter("Dilation", FilterType.DILATION);
        filters.addFilter("Kuwahara", FilterType.KUWAHARA);
        filters.addFilter("RGB Dilation", FilterType.RGB_DILATION);
        filters.addFilter("Sketch", FilterType.SKETCH);
        filters.addFilter("Toon", FilterType.TOON);
        filters.addFilter("Smooth Toon", FilterType.SMOOTH_TOON);
        filters.addFilter("Halftone", FilterType.HALFTONE);

        filters.addFilter("Bulge Distortion", FilterType.BULGE_DISTORTION);
        filters.addFilter("Glass Sphere", FilterType.GLASS_SPHERE);
        filters.addFilter("Haze", FilterType.HAZE);
        filters.addFilter("Laplacian", FilterType.LAPLACIAN);
        filters.addFilter("Non Maximum Suppression", FilterType.NON_MAXIMUM_SUPPRESSION);
        filters.addFilter("Sphere Refraction", FilterType.SPHERE_REFRACTION);
        filters.addFilter("Swirl", FilterType.SWIRL);
        filters.addFilter("Weak Pixel Inclusion", FilterType.WEAK_PIXEL_INCLUSION);
        filters.addFilter("False Color", FilterType.FALSE_COLOR);

        filters.addFilter("Color Balance", FilterType.COLOR_BALANCE);

        filters.addFilter("Levels Min (Mid Adjust)", FilterType.LEVELS_FILTER_MIN);

        filters.addFilter("Bilateral Blur", FilterType.BILATERAL_BLUR);

        filters.addFilter("Transform (2-D)", FilterType.TRANSFORM2D);
        return filters;
    }

    private enum FilterType {
        CONTRAST, GRAYSCALE, SHARPEN, SEPIA, SOBEL_EDGE_DETECTION, THREE_X_THREE_CONVOLUTION, FILTER_GROUP, EMBOSS, POSTERIZE, GAMMA, BRIGHTNESS, INVERT, HUE, PIXELATION,
        SATURATION, EXPOSURE, HIGHLIGHT_SHADOW, MONOCHROME, OPACITY, RGB, WHITE_BALANCE, VIGNETTE, GAUSSIAN_BLUR, CROSSHATCH, BOX_BLUR, CGA_COLORSPACE, DILATION, KUWAHARA, RGB_DILATION, SKETCH, TOON, SMOOTH_TOON, BULGE_DISTORTION, GLASS_SPHERE, HAZE, LAPLACIAN, NON_MAXIMUM_SUPPRESSION,
        SPHERE_REFRACTION, SWIRL, WEAK_PIXEL_INCLUSION, FALSE_COLOR, COLOR_BALANCE, LEVELS_FILTER_MIN, BILATERAL_BLUR, HALFTONE, TRANSFORM2D
    }

    private static class FilterList {
        public List<String> names = new LinkedList<String>();
        public List<FilterType> filters = new LinkedList<FilterType>();

        public void addFilter(final String name, final FilterType filter) {
            names.add(name);
            filters.add(filter);
        }
    }


}
