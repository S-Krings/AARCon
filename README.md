# AARCon
The AARCon (Android-based framework forAugmented Reality with Context-Awareness) aims to ease developing context-aware AR applications in Android by offering a base structure for introducing context-aware adaptations as well as a range of preimplemented classes for context monitoring and adaptation purposes. The framework is available as an Android library written in Java for Google's [ARCore](https://developers.google.com/ar) framework.

## Installation/Setup
- Clone or download AARCon from the master branch's [main page](https://github.com/S-Krings/AARCon) and unpack it if necessary.
- Add the AARConn library folder to your project
  (e.g. in Android Studio: click "File > New > Import Module", select library directory, then click "Finish").
- List AARCon on top of your project's `settings.gradle` file, as shown here:
  ```
  include ':app', ':aarcon'
  ```
- Add a new line to the `dependencies` in the app module's `build.gradle` file, like in the following code snippet:
  ```
  dependencies {
	implementation project(":aarcon")
  }
  ```
- Click "Sync Project with Gradle Files".

## Usage
### Main Components
AARCon has three main components, which are responsible for monitoring context and reacting to detected context changes. They will be introduced here.
#### Condition
- responsible for obtaining information about the different context features
- `abstract`, has to be extended with a subclass for each context feature that is supposed to be monitored
- contains organisational methods for internal communication which the subclasses inherit
- every condition has to register itself to the control class
#### Rule
- responsible for executing adaptations (in response to context changes monitored by conditions)
- `abstract`, has to be extended with a subclass for each kind of action that is supposed to be executed- 
- contains organisational methods for internal communication which the subclasses inherit
- execution is dependant of one or more conditions, which have to be added to the rule
- every rule has to register itself to the control class
#### Control
- responsible for continous context monitoring and quick reaction to changes
- registers and observes all rules and conditions and updates them regularly
- is not supposed to be extended, please use as is

### Extension of Conditions and Rules
As mentioned above, the `Conditon` and `Rule` classes are abstract and have to be extended with subclasses to use them. In each of the classes, there is one method which has to be overridden whith the code executing the subclass's specific purpose.
#### Extending the Condition Class
In the `Condition` class, the `check()` method has to be overridden. Here, the code for checking on one specific context factor has to be entered. It is also advisable to add getters and setters for any objects that are used in the check method. Furthermore, adding a custom constructor can be very helpful to keep the code clean and clutterfree. An example for a `Condition` subclass is the code snippet `DarkCondition`, which detects whether the camera recieves less than 30% lighting.
```
public class DarkCondition extends Condition {

    private ArFragment arFragment;

    public DarkCondition(Control control, ArFragment arFragment) {
        super(control);
        this.arFragment = arFragment;
    }

    @Override
    public boolean check() {
        return 0.3f > arFragment.getArSceneView().getArFrame().getLightEstimate().getPixelIntensity();
    }
}
```
#### Extending the Rule Class
In the `Rule` class, the `exectue()` method has to be overridden with the code for executing a specific adaptation. Again, getters and setters for needed variabes and a custom constructor are advisable. 
In `Rule` subclasses, the `unexecute()` method can also be overriden optionally. it should contain code for clean-up to reverse the effects of the `execute()` method if necessary, as it is called when the specific rule is not active anymore.
Both methods are overridden in the `ChangeColorRule`, which changes the color of the object attached to a `TransformableNode`:
```
package com.example.aarcon.Rules;

import android.app.Activity;
import android.content.Context;

import com.example.aarcon.Control;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ChangeColorRule extends Rule {

    private Activity activity;
    private TransformableNode transformableNode;
    private Color color = new Color(255,0,0); 	//red by default, user can select different color
    private Renderable renderable; 				//internal variable to save the original color

    public ChangeColorRule(Control control, Activity activity, TransformableNode transformableNode) {
        super(control);
        this.activity = activity;
        this.transformableNode = transformableNode;
    }

    public ChangeColorRule(Control control, Activity activity, TransformableNode transformableNode, Color color) {
        super(control);
        this.activity = activity;
        this.transformableNode = transformableNode;
        this.color = color;
    }

    @Override
    public void execute() {
        activity.runOnUiThread(new Runnable() { //runOnUiThread to prevent lagging in the main app
            @Override
            public void run() {					//have to replace renderable to chnge color
                renderable = transformableNode.getRenderable();
                CompletableFuture<Material> materialCompletableFuture =
                        MaterialFactory.makeOpaqueWithColor(activity, color);
                materialCompletableFuture.thenAccept(new Consumer<Material>() {
                    @Override
                    public void accept(Material material) {
                        Renderable renderable = transformableNode.getRenderable().makeCopy();
                        renderable.setMaterial(material);
                        transformableNode.setRenderable(renderable);
                    }
                });
            }
        });
    }

    @Override
    public void unexecute() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (renderable != null){
                    transformableNode.setRenderable(renderable);
                }
            }
        });
    }

    public void setContext(Activity activity) {
        this.activity = activity;
    }

    public void setTransformableNode(TransformableNode transformableNode) {
        this.transformableNode = transformableNode;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

```