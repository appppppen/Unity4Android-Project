using UnityEngine;

public class MobPlugin : SingletonUnity<MobPlugin>
{
#if UNITY_IOS && !UNITY_EDITOR
    [DllImport("__Internal")]
    private static extern void unityToast(string str);
#elif UNITY_ANDROID && !UNITY_EDITOR
    AndroidJavaClass jc;
    AndroidJavaObject activity;
#endif

    public void Start()
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        activity = jc.GetStatic<AndroidJavaObject>("currentActivity");
        activity.Call("SetSplashGone");
#endif
    }

    public void UnityToast(string msg)
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        activity.Call("unityToast", msg);
#elif UNITY_IOS && !UNITY_EDITOR
        unityToast (msg);
#endif
        Debug.Log("UnityToast:" + msg);
    }

    public void AndroidToast(string msg)
    {
        TestAndroid testAndroid = GetComponent<TestAndroid>();
        if (testAndroid!=null)
        {
            testAndroid.Toast(msg);
        }
    }
}