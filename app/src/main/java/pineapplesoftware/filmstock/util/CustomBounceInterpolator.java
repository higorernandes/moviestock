package pineapplesoftware.filmstock.util;

/**
 * Created by Higor Ernandes on 2017-11-18.
 */

public class CustomBounceInterpolator implements android.view.animation.Interpolator
{
    private double mAmplitude = 1;
    private double mFrequency = 10;

    public CustomBounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    @Override
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) * Math.cos(mFrequency * time) + 1);
    }
}