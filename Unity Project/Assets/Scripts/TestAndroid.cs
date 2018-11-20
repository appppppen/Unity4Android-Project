using UnityEngine;
using UnityEngine.UI;

public class TestAndroid : MonoBehaviour {
    public Text text;
    public void UnityToast() {
        MobPlugin.instance.UnityToast("Unity中点击了按钮");
    }
    public void Toast(string msg)
    {
        if (text)
        {
            text.text = msg;
        }
    }
}
