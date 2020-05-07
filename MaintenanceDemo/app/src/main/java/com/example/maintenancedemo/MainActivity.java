package com.example.maintenancedemo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aarcon.Actions.ChangeControlElementsAction;
import com.example.aarcon.Actions.ChangeIndicatorPositionAction;
import com.example.aarcon.Actions.ChangeNodePositionTempAction;
import com.example.aarcon.Actions.ChangePoseToUserAction;
import com.example.aarcon.Actions.ChangeToVoiceInterfaceAction;
import com.example.aarcon.Conditions.DarkCondition;
import com.example.aarcon.Conditions.DistanceToUserBigCondition;
import com.example.aarcon.Conditions.NodeOffScreenCondition;
import com.example.aarcon.Conditions.TrueCondition;
import com.example.aarcon.Conditions.UsedSeldomCondition;
import com.example.aarcon.Control;
import com.example.aarcon.Helpers.NodeToTextHelper;
import com.example.aarcon.Helpers.TextReloadHelper;
import com.example.aarcon.Helpers.UsageCountHelper;
import com.example.aarcon.Actions.ChangeVisibilityAction;
import com.example.aarcon.Actions.ChangeDetailAction;
import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    public ArFragment arFragment;
    private ImageView imageView;
    private Control control;
    private ModelRenderable printerRenderable;
    private ModelRenderable boxRenderable;
    private ModelRenderable cartridgeRenderable;
    private ModelRenderable curvedArrowRenderable;
    private ModelRenderable verticalArrowRenderable;

    private ViewRenderable messagePlaceRenderable;
    private ViewRenderable messageLidRenderable;
    private ViewRenderable messageWaitRenderable;
    private ViewRenderable messageRemoveRenderable;
    private ViewRenderable messageOpenRenderable;
    private ViewRenderable messageInRenderable;
    private ViewRenderable messageCloseRenderable;
    private ViewRenderable successRenderable;


    private AnchorNode anchorNode;
    private TransformableNode node;
    private TransformableNode node2;
    private TransformableNode node4;
    private TransformableNode node5;
    private TransformableNode node6;
    private TransformableNode node7;

    private TransformableNode window;
    private TransformableNode window2;
    private TransformableNode window3;
    private TransformableNode window4;
    private TransformableNode window5;
    private TransformableNode window6;
    private TransformableNode window7;
    private TransformableNode window8;

    private Vector3 printerPosition;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int appOpened;

    private Activity activity = this;
    private ChangeNodePositionTempAction changeNodePositionTempAction;
    private ChangeToVoiceInterfaceAction changeToVoiceInterfaceAction;
    private ChangePoseToUserAction changePoseToUserAction;
    private TextReloadHelper textReloadHelper;
    private DistanceToUserBigCondition distanceBigCondition;


    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    // CompletableFuture requires api level 24
    // FutureReturnValueIgnored is not valid
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        new UsageCountHelper().updateUsageCount(this);

        control = new Control(1f);

        setContentView(R.layout.activity_main);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        imageView = findViewById(R.id.imageView);

        renderPrinter();
        renderBox();
        renderCartridge();
        renderArrowCurved();
        renderArrowVertical();

        renderPlaceMessage();

        //TODO Sphere sphere = (Sphere) node.getRenderable().getCollisionShape();
        //float r = sphere.getRadius();

        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (printerRenderable == null || messagePlaceRenderable == null) { //andyrenderable before
                        return;
                    }

                    Anchor anchor = hitResult.createAnchor();
                    anchorNode = new AnchorNode(anchor);//nchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    placePrinter();
                });
    }


    private void placePrinter(){
        arFragment.setOnTapArPlaneListener(null);
        if(arFragment.getArSceneView().getSession() != null) {
            Config config = arFragment.getArSceneView().getSession().getConfig();
            config.setPlaneFindingMode(Config.PlaneFindingMode.HORIZONTAL);
            arFragment.getArSceneView().getSession().configure(config);
        }
        node = new TransformableNode(arFragment.getTransformationSystem());
        node.setParent(anchorNode);
        node.setRenderable(boxRenderable);
        node.select();
        node.getScaleController().setMinScale(0.18f);
        node.getScaleController().setMaxScale(0.5f);
        printerPosition = new Vector3(node.getLocalPosition().x,node.getLocalPosition().y,node.getLocalPosition().z);

        window = new TransformableNode(arFragment.getTransformationSystem());
        window.setParent(anchorNode);
        window.setRenderable(messagePlaceRenderable);
        reposition(window,0,0.3f,0);
        window.getScaleController().setMinScale(0.4f);
        window.getScaleController().setMaxScale(1f);

        TransformableNode controlNode = new TransformableNode(arFragment.getTransformationSystem());
        controlNode.setParent(anchorNode);
        ChangeControlElementsAction changeControlElementsAction = new ChangeControlElementsAction(control, activity, controlNode);
        changeControlElementsAction.addCondition(new UsedSeldomCondition(control, new UsageCountHelper().getPreferences(activity)));

        renderLidMessage();

        LinearLayout l = (LinearLayout) messagePlaceRenderable.getView();
        Button button = (Button) l.getChildAt(l.getChildCount()-1);

        TrueCondition trueCondition = new TrueCondition(control);
        changeNodePositionTempAction = new ChangeNodePositionTempAction(control,imageView,arFragment,l,window);
        changeNodePositionTempAction.addCondition(trueCondition);

        changeToVoiceInterfaceAction = new ChangeToVoiceInterfaceAction(control,activity, NodeToTextHelper.textFromNode(window,l.getChildCount()-2),button);
        changeToVoiceInterfaceAction.addCondition(new DarkCondition(control,arFragment));

        changePoseToUserAction = new ChangePoseToUserAction(control,arFragment,window);
        changePoseToUserAction.addCondition(trueCondition);

        ChangeDetailAction changeDetailAction = new ChangeDetailAction(control,activity);
        distanceBigCondition = new DistanceToUserBigCondition(control,arFragment,window);
        textReloadHelper = new TextReloadHelper((TextView) l.getChildAt(l.getChildCount()-2),R.string.place_box,activity);
        textReloadHelper.addToOnUpdate(arFragment);
        changeDetailAction.setTextReloadHelper(textReloadHelper);
        changeDetailAction.addCondition(distanceBigCondition);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeControlElementsAction.deleteConditions();
                changeControlElementsAction.deactivate();
                openLid();
            }
        });
    }
    private void openLid(){
        node2 = new TransformableNode(arFragment.getTransformationSystem());
        anchorNode.removeChild(node);
        node2.setParent(anchorNode);
        node2.setRenderable(curvedArrowRenderable);

        node2.getScaleController().setMinScale(0.1f);
        node2.getScaleController().setMaxScale(1f);

        reposition(node2,0f,0.1f,0.1f);
        node2.select();

        window2 = new TransformableNode(arFragment.getTransformationSystem()); //window was andy before
        anchorNode.removeChild(window);
        window2.setParent(anchorNode);
        window2.setRenderable(messageLidRenderable); //andyrenderable before
        window2.getScaleController().setMinScale(0.4f);
        window2.getScaleController().setMaxScale(1f);
        reposition(window2,0,0.4f,0);


        renderWaitMessage();
        LinearLayout l = (LinearLayout) messageLidRenderable.getView();
        Button button = (Button) l.getChildAt(l.getChildCount()-1);

        setActionNodes(window2);
        changeNodePositionTempAction.setViewGroup(l);
        changeToVoiceInterfaceAction.setSpeech(NodeToTextHelper.textFromNode(window2,l.getChildCount()-2));
        changeToVoiceInterfaceAction.setButton(button);
        textReloadHelper.revalue((TextView) l.getChildAt(l.getChildCount()-2),R.string.open_lid);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waiting();
            }
        });
    }

    public void waiting(){
        anchorNode.removeChild(node2);

        window3 = new TransformableNode(arFragment.getTransformationSystem()); //window was andy before
        anchorNode.removeChild(window2);
        window3.setParent(anchorNode);
        window3.setRenderable(messageWaitRenderable); //andyrenderable before
        window3.getScaleController().setMinScale(0.4f);
        window3.getScaleController().setMaxScale(1f);
        reposition(window3,0,0.4f,0);

        renderRemoveMessage();

        LinearLayout l = (LinearLayout) messageLidRenderable.getView();

        setActionNodes(window3);
        changeNodePositionTempAction.setViewGroup(l);
        changeToVoiceInterfaceAction.setSpeech(NodeToTextHelper.textFromNode(window3,l.getChildCount()-1));
        changeToVoiceInterfaceAction.setButton(null);
        textReloadHelper.revalue((TextView) l.getChildAt(l.getChildCount()-2),R.string.wait);


        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message message) {
                takeOut();
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                message.sendToTarget();
            }
        }, 3*1000);
    }

    public void takeOut(){
        node4 = new TransformableNode(arFragment.getTransformationSystem());
        node4.setParent(anchorNode);
        node4.setRenderable(verticalArrowRenderable);

        node4.getScaleController().setMinScale(0.2f);
        node4.getScaleController().setMaxScale(0.5f);
        node4.setLocalPosition(printerPosition);
        reposition(node4,0f,0.1f,0.1f);
        node4.select();

        window4 = new TransformableNode(arFragment.getTransformationSystem()); //window was andy before
        anchorNode.removeChild(window3);
        window4.setParent(anchorNode);
        window4.setRenderable(messageRemoveRenderable); //andyrenderable before
        window4.getScaleController().setMinScale(0.4f);
        window4.getScaleController().setMaxScale(1f);
        reposition(window4, 0, 0.4f, 0);

        renderOpenMessage();
        LinearLayout l = (LinearLayout) messageRemoveRenderable.getView();
        Button button = (Button) l.getChildAt(l.getChildCount()-1);
        textReloadHelper.revalue((TextView) l.getChildAt(l.getChildCount()-2),R.string.remove_cartridge);


        setActionNodes(window4);
        changeNodePositionTempAction.setViewGroup(l);
        changeToVoiceInterfaceAction.setSpeech(NodeToTextHelper.textFromNode(window4,l.getChildCount()-2));
        changeToVoiceInterfaceAction.setButton(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCartridge();
            }
        });
    }

    public void openCartridge(){
        node5 = new TransformableNode(arFragment.getTransformationSystem());
        anchorNode.removeChild(node4);
        node5.setParent(anchorNode);
        node5.setRenderable(cartridgeRenderable);

        node5.getScaleController().setMinScale(2f);
        node5.getScaleController().setMaxScale(8f);
        node5.setLocalPosition(new Vector3(printerPosition.x+0.4f,printerPosition.y+0.2f,printerPosition.z));
        node5.setLocalRotation(Quaternion.axisAngle(Vector3.up(), 90.0f));
        node5.select();

        window5 = new TransformableNode(arFragment.getTransformationSystem()); //window was andy before
        anchorNode.removeChild(window4);
        window5.setParent(anchorNode);
        window5.setRenderable(messageOpenRenderable); //andyrenderable before
        window5.getScaleController().setMinScale(0.4f);
        window5.getScaleController().setMaxScale(1f);
        reposition(window5,0,0.2f,0);

        renderInMessage();
        LinearLayout l = (LinearLayout) messageOpenRenderable.getView();
        Button button = (Button) l.getChildAt(l.getChildCount()-1);
        textReloadHelper.revalue((TextView) l.getChildAt(l.getChildCount()-2),R.string.open_cartridge);


        setActionNodes(window5);
        changeNodePositionTempAction.setViewGroup(l);
        changeToVoiceInterfaceAction.setSpeech(NodeToTextHelper.textFromNode(window5,l.getChildCount()-2));
        changeToVoiceInterfaceAction.setButton(button);

        ImageView imageViewCartridge = findViewById(R.id.imageViewCartridge);
        NodeOffScreenCondition nodeOffScreenConditionCartridge = new NodeOffScreenCondition(control,arFragment,node5);
        ChangeIndicatorPositionAction changeIndicatorPositionAction = new ChangeIndicatorPositionAction(control, arFragment, imageViewCartridge, node5);
        changeIndicatorPositionAction.addCondition(nodeOffScreenConditionCartridge);
        ChangeVisibilityAction changeVisibilityAction = new ChangeVisibilityAction(control,arFragment,imageViewCartridge);
        changeVisibilityAction.addCondition(nodeOffScreenConditionCartridge);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeIndicatorPositionAction.deactivate();
                changeVisibilityAction.deactivate();
                control.deleteAction(changeIndicatorPositionAction);
                control.deleteAction(changeVisibilityAction);
                //combinedCartridgeAction.deleteConditions();
                //control.deleteAction(combinedCartridgeAction);
                imageViewCartridge.setVisibility(View.INVISIBLE);
                putInCartridge();
            }
        });
    }

    public void putInCartridge(){
        node6 = new TransformableNode(arFragment.getTransformationSystem());
        anchorNode.removeChild(node5);
        node6.setParent(anchorNode);
        node6.setRenderable(verticalArrowRenderable);

        node6.getScaleController().setMinScale(0.1f);
        node6.getScaleController().setMaxScale(0.5f);
        node6.setLocalPosition(printerPosition);
        reposition(node6,0f,0.1f,0.1f);

        node6.setLocalPosition(new Vector3(printerPosition.x,printerPosition.y+0.1f,printerPosition.z));
        node6.select();

        window6 = new TransformableNode(arFragment.getTransformationSystem()); //window was andy before
        anchorNode.removeChild(window5);
        window6.setParent(anchorNode);
        window6.setRenderable(messageInRenderable); //andyrenderable before
        window6.getScaleController().setMinScale(0.4f);
        window6.getScaleController().setMaxScale(1f);
        reposition(window6,0,0.4f,0);

        renderCloseMessage();
        LinearLayout l = (LinearLayout) messageInRenderable.getView();
        Button button = (Button) l.getChildAt(l.getChildCount()-1);
        textReloadHelper.revalue((TextView) l.getChildAt(l.getChildCount()-2),R.string.enter_cartridge);


        setActionNodes(window6);
        changeNodePositionTempAction.setViewGroup(l);
        changeToVoiceInterfaceAction.setSpeech(NodeToTextHelper.textFromNode(window6,l.getChildCount()-2));
        changeToVoiceInterfaceAction.setButton(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePrinter();
            }
        });
    }

    public void closePrinter(){
        node7 = new TransformableNode(arFragment.getTransformationSystem());
        anchorNode.removeChild(node6);
        node7.setParent(anchorNode);
        node7.setRenderable(curvedArrowRenderable);

        node7.getScaleController().setMinScale(0.1f);
        node7.getScaleController().setMaxScale(1f);
        reposition(node7,0,0.1f,0);
        node7.setLocalRotation(Quaternion.axisAngle(Vector3.back(), 180.0f));
        node7.setLocalPosition(new Vector3(printerPosition.x,printerPosition.y+0.3f,printerPosition.z));
        node7.select();

        window7 = new TransformableNode(arFragment.getTransformationSystem()); //window was andy before
        anchorNode.removeChild(window6);
        window7.setParent(anchorNode);
        window7.setRenderable(messageCloseRenderable); //andyrenderable before
        window7.getScaleController().setMinScale(0.4f);
        window7.getScaleController().setMaxScale(1f);
        reposition(window7,0,0.4f,0);

        renderSuccessMessage();

        LinearLayout l = (LinearLayout) messageCloseRenderable.getView();
        Button button = (Button) l.getChildAt(l.getChildCount()-1);
        textReloadHelper.revalue((TextView) l.getChildAt(l.getChildCount()-2), R.string.close_lid);


        setActionNodes(window7);
        changeNodePositionTempAction.setViewGroup(l);
        changeToVoiceInterfaceAction.setSpeech(NodeToTextHelper.textFromNode(window7,l.getChildCount()-2));
        changeToVoiceInterfaceAction.setButton(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                success();
                System.out.println("+++++++click");
            }
        });
    }

    public void success(){
        anchorNode.removeChild(node7);
        System.out.println("+++++++");
        window8 = new TransformableNode(arFragment.getTransformationSystem()); //window was andy before
        anchorNode.removeChild(window7);
        window8.setParent(anchorNode);
        window8.setRenderable(successRenderable); //andyrenderable before
        window8.getScaleController().setMinScale(0.4f);
        window8.getScaleController().setMaxScale(1f);
        reposition(window8,0,0.4f,0);

        renderRemoveMessage();

        LinearLayout l = (LinearLayout) successRenderable.getView();

        setActionNodes(window8);
        changeNodePositionTempAction.setViewGroup(l);
        changeToVoiceInterfaceAction.setSpeech(NodeToTextHelper.textFromNode(window8,l.getChildCount()-1));
        changeToVoiceInterfaceAction.setButton(null);
        textReloadHelper.revalue((TextView) l.getChildAt(l.getChildCount()-1),R.string.success);
    }

    private void reposition(TransformableNode node, float x, float y, float z){
        node.setLocalPosition((new Vector3(node.getLocalPosition().x+x,node.getLocalPosition().y+y,node.getLocalPosition().z+z)));
    }

    private void renderPrinter(){
        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        ModelRenderable.builder()
                .setSource(this, Uri.parse("Printer.sfb"))

                .build()
                .thenAccept(renderable -> printerRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    private void renderBox(){
        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        ModelRenderable.builder()
                .setSource(this, Uri.parse("box.sfb"))

                .build()
                .thenAccept(renderable -> boxRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    private void renderCartridge(){
        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        ModelRenderable.builder()
                .setSource(this, Uri.parse("cartridge_twist.sfb"))

                .build()
                .thenAccept(renderable -> cartridgeRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load cardridge renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    private void renderArrowCurved(){
        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        ModelRenderable.builder()
                .setSource(this, Uri.parse("arrow_curved.sfb"))

                .build()
                .thenAccept(renderable -> curvedArrowRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load curved arrow renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    /*private void renderArrowStraight(){
        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        ModelRenderable.builder()
                .setSource(this, Uri.parse("arrow_straight.sfb"))

                .build()
                .thenAccept(renderable -> straightArrowRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load straight arrow renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }*/

    private void renderArrowVertical(){
        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        ModelRenderable.builder()
                .setSource(this, Uri.parse("arrow_vertical.sfb"))

                .build()
                .thenAccept(renderable -> verticalArrowRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load vertical arrow renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    private void renderPlaceMessage(){
        ViewRenderable.builder()
                .setView(this, R.layout.place_printer_layout)
                .build()
                .thenAccept(renderable -> messagePlaceRenderable = renderable);
    }

    private void renderWaitMessage(){
        ViewRenderable.builder()
                .setView(this, R.layout.wait_layout)
                .build()
                .thenAccept(renderable -> messageWaitRenderable = renderable);
    }

    private void renderLidMessage(){
        ViewRenderable.builder()
                .setView(this, R.layout.open_lid_layout)
                .build()
                .thenAccept(renderable -> messageLidRenderable = renderable);
    }

    private void renderRemoveMessage(){
        ViewRenderable.builder()
                .setView(this, R.layout.remove_cartrige_layout)
                .build()
                .thenAccept(renderable -> messageRemoveRenderable = renderable);
    }

    private void renderOpenMessage(){
        ViewRenderable.builder()
                .setView(this, R.layout.open_cartridge_layout)
                .build()
                .thenAccept(renderable -> messageOpenRenderable = renderable);
    }

    private void renderInMessage(){
        ViewRenderable.builder()
                .setView(this, R.layout.cartridge_in_layout)
                .build()
                .thenAccept(renderable -> messageInRenderable = renderable);
    }

    private void renderCloseMessage(){
        ViewRenderable.builder()
                .setView(this, R.layout.close_printer_layout)
                .build()
                .thenAccept(renderable -> messageCloseRenderable = renderable);
    }

    private void renderSuccessMessage(){
        ViewRenderable.builder()
                .setView(this, R.layout.success_layout)
                .build()
                .thenAccept(renderable -> successRenderable = renderable);
    }

    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     * <p>Finishes the activity if Sceneform can not run
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

    private void setActionNodes(TransformableNode node){
        changeNodePositionTempAction.setTransformableNode(node);
        changePoseToUserAction.setTransformableNode(node);
        distanceBigCondition.setTransformableNode(node);
    }
}