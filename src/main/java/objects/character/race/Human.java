package objects.character.race;

import objects.character.limbs.Limb;
import objects.character.limbs.Torso;
import objects.character.limbs.torsoComps.Arm;
import objects.character.limbs.torsoComps.Leg;
import objects.character.limbs.torsoComps.Neck;
import objects.character.limbs.torsoComps.armComps.Hand;
import objects.character.limbs.torsoComps.armComps.handComps.Finger;
import objects.character.limbs.torsoComps.armComps.handComps.Thumb;
import objects.character.limbs.torsoComps.legComps.Foot;
import objects.character.limbs.torsoComps.legComps.footComps.Toe;
import objects.character.limbs.torsoComps.neckComps.Head;
import objects.character.limbs.torsoComps.neckComps.headComps.Ear;
import objects.character.limbs.torsoComps.neckComps.headComps.Eye;
import objects.character.limbs.torsoComps.neckComps.headComps.Mouth;
import objects.character.limbs.torsoComps.neckComps.headComps.Nose;

import java.util.ArrayList;

public class Human implements Race {

    private String name = "Human";

    @Override
    public Torso getLimbs() {
        ArrayList<Limb> limbs = new ArrayList<Limb>();

        Torso torso = getHumanTorso();

/*        for(Arm arm : torso.getArms()){
            for(Finger finger : arm.getHand().getFingers()){
                limbs.add(finger);
            }
            for(Thumb thumb : arm.getHand().getThumbs()){
                limbs.add(thumb);
            }
            limbs.add(arm.getHand());
            limbs.add(arm);
        }

        for(Leg leg : torso.getLegs()){
            for(Toe toe : leg.getFoot().getToes()){
                limbs.add(toe);
            }
            limbs.add(leg.getFoot());
            limbs.add(leg);
        }

        for(Neck neck : torso.getNeck()){
            Head head = neck.getHead();
            for(Eye eye : head.getEyes()){
                limbs.add(eye);
            }
            for(Nose nose : head.getNoses()){
                limbs.add(nose);
            }
            for(Ear ear : head.getEars()){
                limbs.add(ear);
            }
            for(Mouth mouth : head.getMouths()){
                limbs.add(mouth);
            }
            limbs.add(head);
            limbs.add(neck);
        }


        limbs.add(torso);*/

        return torso;
    }

    private Torso getHumanTorso(){
        return new Torso(
                "Torso", new ArrayList<Neck>() {{add(new Neck("Neck", getHumanHead(), 70));}},
                new ArrayList<Arm>() {{
                    add(getHumanLeftArm());
                    add(getHumanRightArm());
                }},
                new ArrayList<Leg>() {{
                    add(getHumanLeftLeg());
                    add(getHumanRightLeg());
                }},
                70
        );
    }

    private Head getHumanHead(){
        return new Head("Head",
                new ArrayList<Eye>() {{
                    add(new Eye("Left eye", 10));
                    add(new Eye("Right eye", 10));
                }},
                new ArrayList<Nose>() {{
                    add(new Nose("Nose", 20));
                }},
                new ArrayList<Ear>() {{
                    add(new Ear("Left ear", 20));
                    add(new Ear("Right ear", 20));
                }},
                new ArrayList<Mouth>() {{
                    add(new Mouth("Mouth", 20));
                }},
                100
        );
    }

    private Arm getHumanRightArm(){
        return new Arm(
                "Right arm", getHumanRightHand(), 50);
    }

    private Arm getHumanLeftArm(){
        return new Arm(
                "Left arm", getHumanLeftHand(), 50);
    }

    private Hand getHumanLeftHand(){
        return new Hand(
                "Left hand", new ArrayList<Finger>() {{
                    add(new Finger("Left pinky", 20));
                    add(new Finger("Left ring finger", 20));
                    add(new Finger("Left middle finger", 20));
                    add(new Finger("Left index finger", 20));
                }},
                new ArrayList<Thumb>() {{
                    add(new Thumb("Left thumb", 20));
                }},
                20
        );
    }

    private Hand getHumanRightHand(){
        return new Hand(
                "Right hand", new ArrayList<Finger>() {{
            add(new Finger("Right pinky", 20));
            add(new Finger("Right ring finger", 20));
            add(new Finger("Right middle finger", 20));
            add(new Finger("Right index finger", 20));
        }},
                new ArrayList<Thumb>() {{
                    add(new Thumb("Right thumb", 20));
                }},
                20
        );
    }

    private Leg getHumanRightLeg(){
        return new Leg("Right leg",
                new Foot(
                "Right foot", new ArrayList<Toe>(){{
                    add(new Toe("Right little toe", 10));
                    add(new Toe("Right fourth toe", 10));
                    add(new Toe("Right middle toe", 10));
                    add(new Toe("Right index toe", 10));
                    add(new Toe("Right big toe", 10));
                }},
                20
        ),50);
    }

    private Leg getHumanLeftLeg(){
        return new Leg("Left leg",
                new Foot(
                "Left foot", new ArrayList<Toe>(){{
            add(new Toe("Left little toe", 10));
            add(new Toe("Left fourth toe", 10));
            add(new Toe("Left middle toe", 10));
            add(new Toe("Left index toe", 10));
            add(new Toe("Left big toe", 10));
        }},
                20
        ),50);
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "Human";
    }
}
