using UnityEngine;
using System.Collections;

public class SingletonUnity<T> : MonoBehaviour where T : MonoBehaviour
{	
	public static T instance = null;
    public virtual void Awake()
    {
        if (instance == null)
        {
            DontDestroyOnLoad(gameObject);
            instance = (T)FindObjectOfType(typeof(T));
        }
        else if (instance != null)
        {
            Destroy(gameObject);
        }
    }
}