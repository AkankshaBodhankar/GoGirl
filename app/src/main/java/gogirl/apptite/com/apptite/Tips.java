package gogirl.apptite.com.apptite;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 09-Jul-16.
 */
public class Tips extends  Activity {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tips);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
             //   Toast.makeText(getApplicationContext(),
                    //    listDataHeader.get(groupPosition) + " Expanded",
                  //      Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
             //   Toast.makeText(getApplicationContext(),
               //         listDataHeader.get(groupPosition) + " Collapsed",
                 //       Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Pepper spray");
        List<String> q0 = new ArrayList<String>();
        q0.add("Pros and cons. Pepper spray, like other self-defence aids, can be a useful tool. However, it is important to understand that there can be significant drawbacks to its use. For example, did you know that it doesn’t work on everyone? Surprisingly, 15-20% of people will not be incapacitated even by a full-face spray. Also, if you’re carrying it in your purse, you will only waste time and alert the attacker to your intentions while you fumble for it. Never depend on any self-defence tool or weapon to stop an attacker. Trust your body and your wits, which you can always depend on in the event of an attack.");
        listDataChild.put(listDataHeader.get(0), q0);

        listDataHeader.add(" Self-defense training");
        List<String> q1 = new ArrayList<String>();
        q1.add("It is important to evaluate the goals and practical usefulness of a women’s self-defense program before signing up. Here are two tips:\n" +
                "\n" +
                "a) Avoid martial arts studios unless you specifically wish to train in the traditional martial arts techniques and are prepared for a long-term commitment. Many women’s self-defense programs teach watered-down martial arts techniques that are complex and unrealistic under the stress of an actual attack;\n" +
                "\n" +
                "b) The self-defense program should include simulated assaults, with a fully padded instructor in realistic rape and attack scenarios, to allow you to practice what you’ve learned.");
        listDataChild.put(listDataHeader.get(1), q1);

        listDataHeader.add("  Use your sixth sense");
        List<String> q2 = new ArrayList<String>();
        q2.add(" “Sixth sense.” “Gut instinct.” Whatever you call it, your intuition is a powerful subconscious insight into situations and people. All of us, especially women, have this gift, but very few of us pay attention to it. Learn to trust this power and use it to your full advantage. Avoid a person or a situation which does not “feel” safe–you’re probably right.");
        listDataChild.put(listDataHeader.get(2), q2);

        listDataHeader.add(" Escape");
        List<String> q3 = new ArrayList<String>();
        q3.add("Always your best option. What if the unthinkable happens? You are suddenly confronted by a predator who demands that you go with him–be it in a car, or into an alley, or a building. It would seem prudent to obey, but you must never leave the primary crime scene. You are far more likely to be killed or seriously injured if you go with the predator than if you run away (even if he promises not to hurt you). Run away, yell for help, throw a rock through a store or car window–do whatever you can to attract attention. And if the criminal is after your purse or other material items, throw them one way while you run the other.");
        listDataChild.put(listDataHeader.get(3), q3);

        listDataHeader.add("Your right to fight");
        List<String> q4 = new ArrayList<String>();
        q4.add("Unfortunately, no matter how diligently we practice awareness and avoidance techniques, we may find ourselves in a physical confrontation. Whether or not you have self-defence training, and no matter what your age or physical condition, it is important to understand that you CAN and SHOULD defend yourself physically. You have both the moral and legal right to do so, even if the attacker is only threatening you and hasn’t struck first. Many women worry that they will anger the attacker and get hurt worse if they defend themselves, but statistics clearly show that your odds of survival are far greater if you do fight back. Aim for the eyes first and the groin second. Remember, though, to use the element of surprise to your advantage–strike quickly, and mean business. You may only get one chance.");
        listDataChild.put(listDataHeader.get(4), q4);

        listDataHeader.add("Avoiding a car-jacking. Lock all doors and keep windows up when driving");
        List<String> q5 = new ArrayList<String>();
        q5.add(" Most car-jackings take place when vehicles are stopped at intersections. The criminals approach at a 45-degree angle (in the blind spot), and either pull you out of the driver’s seat or jump in the passenger’s seat.");
        listDataChild.put(listDataHeader.get(5), q5);

        listDataHeader.add("Safety in cyberspace");
        List<String> q6 = new ArrayList<String>();
        q6.add("Although the Internet is educational and entertaining, it can also be full of danger if one isn’t careful. When communicating on-line, use a nickname and always keep personal information such as home address and phone number confidential. Instruct family members to do the same. Keep current on security issues, frauds, viruses, etc. by periodically referring to “The Police Notebook” Internet Safety Page.");
        listDataChild.put(listDataHeader.get(6), q6);

        listDataHeader.add(" A travel tip");
        List<String> q7 = new ArrayList<String>();
        q7.add("Violent crimes against women happen in the best and worst hotels around the world. Predators may play the part of a hotel employee, push their way through an open or unlocked door, or obtain a pass key to the room. As with home safety, never open your door unless you are certain the person on the other side is legitimate, and always carry a door wedge with you when you travel. A wedge is often stronger than the door it secures.");
        listDataChild.put(listDataHeader.get(7), q7);

        listDataHeader.add(" Home invasions");
        List<String> q8 = new ArrayList<String>();
        q8.add("A crime on the rise. The primary way to prevent a home invasion is simply to never, ever open your door unless you either are certain you know who’s on the other side or can verify that they have a legitimate reason for being there (dressing up as a repair person or even police officer is one trick criminals use). In the event that an intruder breaks in while you’re home, you should have a safe room in your house to which you can retreat. Such a room should be equipped with a strong door, deadbolt lock, phone (preferably cell phone), and a can of pepper spray or fire extinguisher.");
        listDataChild.put(listDataHeader.get(8), q8);

        listDataHeader.add("Awareness");
        List<String> q9 = new ArrayList<String>();
        q9.add("Your first line of defence. Most people think of kicks to the groin and blocking punches when they hear the term “self-defence.” However, true self-defence begins long before any actual physical contact. The first, and probably most important, component in self-defence is awareness: awareness of yourself, your surroundings, and your potential attacker’s likely strategies.\n" +
                "\n" +
                "The criminal’s primary strategy is to use the advantage of surprise. Studies have shown that criminals are adept at choosing targets who appear to be unaware of what is going on around them. By being aware of your surroundings and by projecting a “force presence,” many altercations which are commonplace on the street can be avoided.");
        listDataChild.put(listDataHeader.get(9), q9);

        listDataHeader.add("If you see something suspicious, report it");
        List<String> q10 = new ArrayList<String>();
        q10.add("Don’t worry about whether you’re overreacting or being overcautious. If you think something is out of place, make a call to the authorities. It’s always better to be safe than sorry. That’s what the police are there for. It’s better all the way around if they can be proactive instead of reactive.");
        listDataChild.put(listDataHeader.get(10), q10);

        listDataHeader.add("If you have to defend yourself, aim for body parts on your attacker that are tender and cannot be strengthened");
        List<String> q11 = new ArrayList<String>();
        q11.add("If you find yourself in a position where you have to defend yourself, you want to aim wherever it’s going to hurt the most regardless of the attacker’s strength and size. These areas are the eyes, nose, throat, groin and shin. You can’t toughen them up so it doesn’t matter if he’s twice your size; hit him in one of these spots and he’s going to feel some pain.");
        listDataChild.put(listDataHeader.get(11), q11);

        listDataHeader.add("Set boundaries and enforce them");
        List<String> q12 = new ArrayList<String>();
        q12.add("   If someone comes too close for comfort, tell them and force them to move back and give you your space. Don’t worry about whether you’ll come across as short or impolite. If your safety is at stake, it’s no time to be nice.");
        listDataChild.put(listDataHeader.get(12), q12);

        listDataHeader.add(" If someone tries to force you to a different location, do not go");
        List<String> q13 = new ArrayList<String>();
        q13.add("Research has shown over and over again that if someone intends to move you, they also intend for you to not make it out alive. Whatever you do, never get in a vehicle with someone regardless of what they tell you because it isn’t going to be good.");
        listDataChild.put(listDataHeader.get(13), q13);

        listDataHeader.add("Don’t open your house door without knowing who is on the other side");
        List<String> q14 = new ArrayList<String>();
        q14.add("Even if it’s someone who claims to be from the electric company, if you’re not expecting them then make them wait while you call yourself and verify their identity. The minute you open your door, that person has access not only to your home, but to you and your family as well.");
        listDataChild.put(listDataHeader.get(14), q14);

        listDataHeader.add("Don’t leave things lying around the yard that someone could use to access your home");
        List<String> q15 = new ArrayList<String>();
        q15.add("If you’re working on repainting the exterior of your house, take the ladder in at night. Yes, it’s a pain, but it’s much less painful than someone gaining entry when you’re lost in dreamland and can’t protect yourself.");
        listDataChild.put(listDataHeader.get(15), q15);

        listDataHeader.add("Keep the bushes around your house trimmed so that someone doesn’t have a place to hide");
        List<String> q16= new ArrayList<String>();
        q16.add("Better yet, when you plant bushes beneath your windows, make sure they’re thorny and would hurt if someone got too close to them. Make your home unfriendly to anyone who wants to access it without your permission.");
        listDataChild.put(listDataHeader.get(16), q16);

        listDataHeader.add("Carry a weapon only if you’re trained and willing to use it");
        List<String> q17 = new ArrayList<String>();
        q17.add("Some women choose to carry pepper spray or a firearm as a method of defense. While that’s fine, make sure that you know how to use your weapon of choice properly and effectively. Also, don’t carry anything if you’re not willing to use it because then it just becomes something that the bad guy can take and use against you.");
        listDataChild.put(listDataHeader.get(17), q17);

        listDataHeader.add(" What is the default value of byte datatype in Java?");
        List<String> q18 = new ArrayList<String>();
        q18.add("Default value of byte datatype is 0.");
        listDataChild.put(listDataHeader.get(18), q18);

        listDataHeader.add("Trust your gut");
        List<String> q19 = new ArrayList<String>();
        q19.add("If you feel like something is out of place, trust your instinct. Your sixth sense is actually your mind picking up on miniscule things that your brain doesn’t exactly register. So, if the hair on the back of your neck starts to raise, pay attention because something isn’t right.");
        listDataChild.put(listDataHeader.get(19), q19);

        listDataHeader.add("Never, ever give up!");
        List<String> q20 = new ArrayList<String>();
        q20.add("No matter what happens, never stop fighting. Unleash your inner bitch and let her go. If you’re in a fight for your life, make sure you’re the one who wins. It’s time to play dirty and let the cards fall where they may. Also, realize that even if you’re injured (which you most likely would be in a self-defense situation), it doesn’t mean it’s a critical injury. Just keep fighting and have faith that you will persevere.\n" +
                "The only thing that’s important is that, at the end of the day, you’re the one who lives to see another day. You want to make sure you come home every night and get the time you expect and deserve with your family and friends.\n" +
                "Don’t let someone else rob you of your dreams and goals. If they choose you as a target, make sure they know that they just made a critical error in judgment.");
        listDataChild.put(listDataHeader.get(20), q20);



        // Adding child data

    }
}







