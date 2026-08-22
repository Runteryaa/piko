/*
 * Copyright (C) 2026 piko <https://github.com/crimera/piko>
 *
 * See the included NOTICE file for GPLv3 §7(b) terms that apply to this code.
 */


package app.morphe.extension.instagram.patches.overflowMenuButton.reels.buttons;

import static app.morphe.extension.instagram.utils.IgStr.str;

import android.content.Intent;
import android.view.View;
import android.content.Context;

import app.morphe.extension.shared.Logger;
import app.morphe.extension.instagram.entity.MediaData;

/**
 * A reel overflow menu button that fires a system share intent with the
 * current reel''s Instagram link. This button is added when the user has
 * the "Hide reshare button" setting enabled, moving the action from the
 * visible action bar into the overflow menu.
 */
public class ReshareButton extends ReelButton {

    public ReshareButton(Context context, Object mediaObject) {
        super(context, mediaObject);
    }

    @Override
    public void onClick(View view) {
        try {
            MediaData mediaData = new MediaData(this.mediaObject);
            String shortcode = mediaData.getShortcode();
            // Reels use /reel/ path on Instagram web
            String reelUrl = "https://www.instagram.com/reel/" + shortcode + "/";

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, reelUrl);
            this.context.startActivity(Intent.createChooser(shareIntent, str("piko_reshare_post")));
        } catch (Exception e) {
            Logger.printException(() -> "ReshareButton onClick failure", e);
        }
    }
}
