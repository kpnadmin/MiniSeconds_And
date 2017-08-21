using UnityEngine;
using System.Collections;

public class ScoreManagerScript : MonoBehaviour {

    public static int Score { get; set; }


	void Start () {
		//10.100의 자릿수 숨김
        (Tens.gameObject as GameObject).SetActive(false); 
        (Hundreds.gameObject as GameObject).SetActive(false);
	}
	

	void Update () {

        if (previousScore != Score) 
        { 
            if(Score < 10)
            {
                //일의 자릿수
                Units.sprite = numberSprites[Score];
            }
            else if(Score >= 10 && Score < 100)
            {
				//10의 자릿수
                (Tens.gameObject as GameObject).SetActive(true);
                Tens.sprite = numberSprites[Score / 10];
                Units.sprite = numberSprites[Score % 10];
            }
            else if(Score >= 100)
            {
				//100의 자릿수
                (Hundreds.gameObject as GameObject).SetActive(true);
                Hundreds.sprite = numberSprites[Score / 100];
                int rest = Score % 100;
                Tens.sprite = numberSprites[rest / 10];
                Units.sprite = numberSprites[rest % 10];
            }
        }

	}


    int previousScore = -1;
    public Sprite[] numberSprites;
    public SpriteRenderer Units, Tens, Hundreds;
}
