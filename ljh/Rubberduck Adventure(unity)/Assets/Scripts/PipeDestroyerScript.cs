using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using UnityEngine;


class PipeDestroyerScript : MonoBehaviour
{
    void OnTriggerEnter2D(Collider2D col)
    {
        if (col.tag == "Pipe" || col.tag == "Pipeblank")
            Destroy(col.gameObject.transform.parent.gameObject); //파이프를 지워 메모리 확보 
        }
    }

