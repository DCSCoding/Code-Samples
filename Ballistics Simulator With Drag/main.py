# This script is designed to simulate ballistic trajectory, and includes a rudimentary drag calculation.
# In addition, you can set whether or not there is a floor for the projectile to collide with, and if attractive direction of gravity
# should switch when the projectile passes the equator. (Parameters hasFloor and gFlip)

import math
import matplotlib.pylab as plot


def update(position, positions, velocity_x, velocity_y, angle_radians, mass_kg, gravity, gravity_flip,
           gas_density, drag_coefficient, cross_sectional_area, time_step_seconds, has_floor, floor_height, stop):
    temp_y_pos = position[1]
    temp_v_x = velocity_x
    temp_v_y = velocity_y
           
    # Updates the force of drag based on current parameters (namely the current velocity)
    drag_x = -.5 * drag_coefficient * gas_density * cross_sectional_area * velocity_x ** 2
    drag_y = -.5 * drag_coefficient * gas_density * cross_sectional_area * velocity_y ** 2
           
    if velocity_y < 0:
        drag_y = .5 * drag_coefficient * gas_density * cross_sectional_area * velocity_y ** 2
    # Updates the acceleration based on gravity / drag
    accel_x = (drag_x / mass_kg)
    accel_y = ((drag_y / mass_kg) + gravity)
    
    # Updates velocityy based on acceleration and length of the time step.
    velocity_x = velocity_x + accel_x * time_step_seconds
    velocity_y = velocity_y + accel_y * time_step_seconds

    # This part just ends the loop if you've set a floor and the projectile hits that floor. It also fixes the x
    # coord within some small margin of error.

    if has_floor and position[1] + (velocity_y + temp_v_y) / 2 * time_step_seconds <= floor_height and velocity_y <= 0:

        step_ratio = position[1] / abs((velocity_y + temp_v_y) / 2)
        position[1] = floor_height

        velocity_x *= step_ratio
        temp_v_x *= step_ratio

        position[0] += (velocity_x + temp_v_x) / 2
        position[1] += (velocity_y + temp_v_y) / 2

        stop = True

    else:
        position[0] += (velocity_x + temp_v_x) / 2 * time_step_seconds
        position[1] += (velocity_y + temp_v_y) / 2 * time_step_seconds

        # If the object crosses over the median, this will flip gravity and account for the overshoot.
        
        if temp_y_pos > 0 and position[1] < 0:
            # Figure out how much time it takes to go from the position at the start of the frame to the median.
            time = (math.sqrt(2 * accel_y * temp_y_pos + temp_v_y ** 2) + temp_v_y) / accel_y

            # Figure out its velocity when it hits the median.
            v0 = temp_v_y + accel_y * time

            # Figure out what it's velocity should have been at the end of this time step, had we not fucked it up.
            v1 = v0 + (accel_y - gravity - gravity) * (time_step_seconds - time)

            # Figure out what the avg velocity between v0 and v1
            avgv0v1 = (v0 + v1) / 2

            # update velocity_y so it's correct.
            velocity_y = v1

            # set position[1] to where it rightly should be beneath the median.
            position[1] = (avgv0v1 * (time_step_seconds - time))
            # flip gravity
            if gravity_flip and position[1] < 0:
                gravity *= -1

        elif temp_y_pos < 0 and position[1] > 0:

            # This is the same equation as above but accounting for the fact that we're coming up from below.

            time = (math.sqrt(2 * accel_y * temp_y_pos + temp_v_y ** 2) - temp_v_y) / - accel_y

            v0 = temp_v_y + accel_y * time

            v1 = v0 + (accel_y - gravity - gravity) * (time_step_seconds - time)

            avgv0v1 = (v0 + v1) / 2

            position[1] = (avgv0v1 * (time_step_seconds - time))
            velocity_y = v1

            if gravity_flip and position[1] > 0:
                gravity *= -1
        # print("Avg. X velocity: " + str((vx+tempvx)/2))
        # print("Avg. Y velocity: " + str((vy + temp_v_y) / 2))

        positions += position

    return (position, positions, velocity_x, velocity_y, angle_radians, mass_kg, gravity, gravity_flip,
            gas_density, drag_coefficient, cross_sectional_area, time_step_seconds, has_floor, floor_height, stop)


position = [0, 0]
velocity = 30
angle = math.radians(45)
vx = velocity * math.cos(angle)
vy = velocity * math.sin(angle)
mass = .124
gravity = -9.81
gasdensity = 1.225
dragco = .5
diameter = 7.5
csa = (diameter / 2 / 100) ** 2 * math.pi
floor = 0
timestepsec = .001
gflip = True
hasfloor = False
positions = [0, 0]
iterations = 250000
stop = False

state = (position, positions, vx, vy, angle, mass, gravity, gflip, gasdensity, dragco, csa, timestepsec, hasfloor,
           floor, stop)

for i in range(iterations):
    state = update(state[0], state[1], state[2], state[3], state[4], state[5], state[6], state[7],
                     state[8], state[9], state[10], state[11], state[12], state[13], state[14])
    if state[14]:
        break
# print(positions)

x = []
y = []
for i in range(len(positions)):
    if i % 2 == 0:
        x.append(positions[i])
    else:
        y.append([positions[i]])


plot.plot(x, y)
plot.show()
# See PyCharm help at https://www.jetbrains.com/help/pycharm/
