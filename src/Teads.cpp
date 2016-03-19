#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <set>
#include <map>
#include <list>
#include <utility>
#include <iterator>

using namespace std;

typedef list <int> TConnection;
typedef map <int, TConnection> TConnections;

class TNode
    {
        //private:
        public: //should be changed to private
        int name;
        int depth;
        TNode *father;
        
        public:
        TNode(int vname, TNode *vfather, int vdepth)
        { 
            name = vname;
            father = vfather;
            depth = vdepth;
        }
        void WalkTree ();
        //TNode* GetFather();
        //int GetName();
        // friend void CountSteps (TNodes*);
        //friend void BuildTree(TConnections *v);
        ~TNode();
    };

    typedef map <int, TNode*> TBuilt_node;
    typedef set <TNode*> TNodes;
    TConnections connections;
    TBuilt_node built_nodes;
    TBuilt_node::iterator it_built;
    TNode *node_from, *node_to, *root;
    TNodes roots, leaf_nodes;
    TNodes::iterator itr;
    set <int> leaves;
    set <int> nodes;
    int n; // the number of adjacency relations
    int from, to, answer;
    
    /*
    int TNode::GetName ()
    {
        return this->name;
    }    
    
    TNode* TNode::GetFather ()
    {
        return this->father;
    } 
    */   
    
    void TNode::WalkTree ()
    {
        if (this->father != nullptr)
        {
            if ( (this->depth + 1) > (this->father)->depth ) 
            {
                (this->father)->depth = this->depth + 1;
                answer = this->depth + 1;
            }        
             //  cerr << " name " << this->name << " depth " << this->depth << " - ";
        }   
    }
        
    void CountSteps (TNodes *vleaf_nodes)
    {
        TNodes next_nodes;
            
        next_nodes.clear();
        for (itr = vleaf_nodes->begin(); itr != vleaf_nodes->end(); ++itr)
        {
            if ( (*itr) != nullptr ) 
            {
                (*itr)->WalkTree();
                next_nodes.insert((*itr)->father);
            }    
        }
           // cerr << endl << " Next nodes" << endl;
      //  for (itr = next_nodes.begin(); itr != next_nodes.end(); ++itr)
          //  cerr << endl;    
          next_nodes.erase(root);
       // if ( next_nodes.find(root) != next_nodes.end() ) next_nodes.erase(root);
            if (!next_nodes.empty()) CountSteps(&next_nodes);
           // else cerr << endl << "end of cycle" << endl;
    }
  
    void BuildTree(TConnections *vconnect)
    {
        TConnection::iterator it3;
        set <int> created;
            
        ////    cerr << "vconnect" << endl;
        ////    cerr << "father | children" << endl;
        for (auto it2 = vconnect->begin(); it2 != vconnect->end(); ++it2)
        {
            if (created.insert((*it2).first).second) 
            {
                node_to = new TNode((*it2).first,nullptr,0);
                built_nodes.insert(make_pair((*it2).first,node_to));
                roots.insert(node_to);
                ////   cerr << (*it2).first << " | ";
            }
            else
            {
                node_to = built_nodes[(*it2).first];
                built_nodes.insert(make_pair((*it2).first,node_to)); //???????????
                ////   cerr << (*it2).first << " | ";
            }
            for (it3 = (*it2).second.begin(); it3 != (*it2).second.end(); ++it3)
            {
                if (created.insert(*it3).second)
                {
                      cerr << *it3 << " ";
                    node_from = new TNode(*it3,node_to,0);
                    built_nodes.insert(make_pair(*it3,node_from));
                }
                else
                {
                      cerr << *it3 << " ";
                    built_nodes[*it3]->father = node_to;
                    roots.erase(built_nodes[*it3]);
                }
            }
            ////    cerr << endl;
        }
            
          /*  if (! built_nodes.empty()) cerr << endl << "Built nodes" << endl;
            for (auto itn = built_nodes.begin(); itn != built_nodes.end(); ++itn)
            {    
                cerr << (*itn).second->name << " <- ";
                if ((*itn).second->father != nullptr) cerr << (*itn).second->father->name << endl;
                else cerr << "no father" << endl;
            }  */
    }    

int main()
{
    answer = 0;
    cin >> n; cin.ignore();
    for (int i = 0; i < n; ++i)
    {
        cin >> from >> to; cin.ignore();
        if ( nodes.insert(from).second ) leaves.insert(from);
        else leaves.erase(from);
        if ( nodes.insert(to).second ) leaves.insert(to);
        else leaves.erase(to);
        connections[from].push_back(to);
    }
   // cerr << endl;
    BuildTree(&connections);
    
    if (!leaves.empty()) 
    {
            //cerr << endl << "Leaves" << endl;
        for (auto it6 = leaves.begin(); it6 != leaves.end(); ++it6)
        {
               // cerr << *it6 << " ";
            if (built_nodes[*it6]->father == nullptr) root = built_nodes[*it6];
            leaf_nodes.insert(built_nodes[*it6]);
        }
            if (root != nullptr) cerr << endl << "Root is from leaves " << root->name << endl;
    }   
    else cerr << "There are no leaves" << endl;
    if (!roots.empty())
    {
        itr = roots.begin();
        root = *itr;
            cerr << endl << "Root is not from leaves " << root->name << endl;
    }
    leaf_nodes.erase(root);
    CountSteps(&leaf_nodes);
       cerr << "root " << root->name;
    if ( (leaves.insert(root->name).second) && (answer == root->depth) )
    {
           cerr << " Root is not from leaves" << endl;
           cerr << endl << "answer is saved in root " << root->depth << endl;
        cout << root->depth;
    }
    else 
    if ( (!leaves.insert(root->name).second) || (answer != root->depth) )
    {
           cerr << " Root is from leaves" << endl;
           cerr << " depth to be divided by two " << root->depth << endl;
        int ans = (root->depth)/2 + (root->depth)%2;
        answer = answer/2 + answer%2;
          cerr << endl << "answer is saved in root" << ans << endl;
        cout << answer;
    }
}    