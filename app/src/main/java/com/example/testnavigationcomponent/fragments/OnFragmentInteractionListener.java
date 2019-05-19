package com.example.testnavigationcomponent.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */
public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Bundle data);

    void onFragmentStarted(Fragment context, Bundle data);

    void onFragmentStop(Fragment context, Bundle data);
}
